param([string]$JavaHome = 'C:/Users/kapit/.jdks/openjdk-25.0.2')
$ErrorActionPreference = 'Stop'
$workspace = [IO.Path]::GetFullPath((Join-Path $PSScriptRoot '../..'))
$classpathFile = [IO.Path]::GetTempFileName()
$argumentsFile = [IO.Path]::GetTempFileName()
$previousJavaHome = $env:JAVA_HOME
Push-Location $workspace
try {
    $env:JAVA_HOME = $JavaHome
    & ./mvnw.cmd -q dependency:build-classpath "-Dmdep.outputFile=$classpathFile" '-Dmdep.regenerateFile=true'
    if ($LASTEXITCODE -ne 0) { throw 'Maven classpath generation failed' }
    $classpath = [IO.File]::ReadAllText($classpathFile).Trim().Replace('\', '/')
    $source = (Join-Path $PSScriptRoot 'ConfigFixtureProbe.java').Replace('\', '/')
    [IO.File]::WriteAllText($argumentsFile, "-cp`n`"$classpath`"`n`"$source`"")
    & "$JavaHome/bin/java.exe" "@$argumentsFile"
    if ($LASTEXITCODE -ne 0) { throw 'Spring configuration fixture failed' }
}
finally {
    $env:JAVA_HOME = $previousJavaHome
    Pop-Location
    Remove-Item -LiteralPath $classpathFile, $argumentsFile -Force
}
