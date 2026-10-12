param(
    [string]$BaseUrl = 'http://localhost:18082',
    [int]$FromChapter = 29,
    [int]$ToChapter = 37
)
$ErrorActionPreference = 'Stop'
if ($BaseUrl -notmatch '^http://(localhost|127[.]0[.]0[.]1):[0-9]+$') {
    throw 'This verifier is intended for the isolated local validation backend.'
}
$workspace = [IO.Path]::GetFullPath((Join-Path $PSScriptRoot '../..'))
$login = Invoke-RestMethod -Method Post -Uri "$BaseUrl/api/auth/login" -ContentType 'application/json' -Body '{"email":"linux-validation@example.invalid","password":"Validation-Local-2026"}'
$headers = @{ Authorization = "Bearer $($login.token)" }
function Compare-Lesson([string]$Chapter, [string]$Lesson) {
    $file = Join-Path $workspace "src/main/resources/content/$Chapter/$Lesson.json"
    $expected = Get-Content -LiteralPath $file -Raw | ConvertFrom-Json
    $route = "$BaseUrl/api/chapters/$Chapter/lessons/$Lesson"
    $theory = Invoke-RestMethod -Uri "$route/theory" -Headers $headers
    $exercises = Invoke-RestMethod -Uri "$route/exercises" -Headers $headers
    $quiz = Invoke-RestMethod -Uri "$route/quiz" -Headers $headers
    if ($theory.Count -ne $expected.theory.Count -or $exercises.Count -ne $expected.exercises.Count -or $quiz.questionCount -ne $expected.quiz.Count) {
        throw "API/source mismatch for $Chapter/$Lesson"
    }
    for ($i = 0; $i -lt $exercises.Count; $i++) {
        foreach ($field in 'prompt', 'hint', 'solution') {
            if ($exercises[$i].$field -cne $expected.exercises[$i].$field) {
                throw "Exercise $($i+1) $field differs: $Chapter/$Lesson"
            }
        }
    }
    for ($i = 0; $i -lt $theory.Count; $i++) {
        foreach ($field in 'type', 'heading', 'body', 'code') {
            if ([string]$theory[$i].$field -cne [string]$expected.theory[$i].$field) {
                throw "Theory $($i+1) $field differs: $Chapter/$Lesson"
            }
        }
    }
    $expectedDraw = [Math]::Min(20, $expected.quiz.Count)
    if ($quiz.drawSize -ne $expectedDraw -or $quiz.passPercent -ne 80) {
        throw "Unexpected quiz policy for $Chapter/$Lesson"
    }
}
$chapters = Invoke-RestMethod -Uri "$BaseUrl/api/chapters?track=LINUX" -Headers $headers
$checked = 0
foreach ($chapter in $chapters) {
    if ($chapter.slug -notmatch '^_lx_([0-9]{2})_') { throw 'Unexpected Linux chapter slug' }
    $number = [int]$Matches[1]
    if ($number -lt $FromChapter -or $number -gt $ToChapter) { continue }
    $lessons = Invoke-RestMethod -Uri "$BaseUrl/api/chapters/$($chapter.slug)/lessons" -Headers $headers
    $files = Get-ChildItem -LiteralPath (Join-Path $workspace "src/main/resources/content/$($chapter.slug)") -Filter '*.json'
    if ($lessons.Count -ne $files.Count -or $chapter.lessonCount -ne $files.Count) { throw "Lesson list mismatch: $($chapter.slug)" }
    foreach ($lesson in $lessons) {
        Compare-Lesson $chapter.slug $lesson.slug
        $checked++
    }
}
if ($checked -eq 0) { throw 'No lessons were checked.' }
foreach ($track in 'JAVA', 'JAVASCRIPT') {
    $otherChapters = Invoke-RestMethod -Uri "$BaseUrl/api/chapters?track=$track" -Headers $headers
    $chapter = $otherChapters[0]
    $otherLessons = Invoke-RestMethod -Uri "$BaseUrl/api/chapters/$($chapter.slug)/lessons" -Headers $headers
    Compare-Lesson $chapter.slug $otherLessons[0].slug
}
Write-Output "OK: $checked Linux lessons ($FromChapter-$ToChapter), exact theory/exercise contents, quiz counts/policy; Java and JavaScript regression."
