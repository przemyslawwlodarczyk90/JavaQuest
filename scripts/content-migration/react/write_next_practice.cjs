// Odtwarza ukończone zestawy wprowadzenia oraz TypeScriptu, bez zmiany teorii.
// Uruchomienie: node scripts/content-migration/react/write_next_practice.cjs
const fs = require('node:fs');
const path = require('node:path');
const assert = require('node:assert/strict');

const entries = [
  ['02_HowReactWorksDeclarativeUi', './practice_02.cjs', 30, 100],
  ['03_ReactHistoryAndEcosystemMap', './practice_03.cjs', 20, 60],
  ['04_EnvironmentNodeAndVite', './practice_04.cjs', 20, 60],
  ['05_ProjectAnatomyAndBuildPipeline', './practice_05.cjs', 30, 100],
  ['06_ToolingEslintPrettierDevtools', './practice_06.cjs', 30, 100],
  ['01_WhatIsTypeScriptAndWhy', './practice_ts_01.cjs', 20, 60, '_react_01_typescript_wprowadzenie'],
  ['02_BasicTypesAndInference', './practice_ts_02.cjs', 30, 100, '_react_01_typescript_wprowadzenie'],
  ['03_ObjectTypesInterfacesAndOptional', './practice_ts_03.cjs', 30, 100, '_react_01_typescript_wprowadzenie'],
  ['04_UnionsLiteralsAndNarrowing', './practice_ts_04.cjs', 30, 100, '_react_01_typescript_wprowadzenie'],
  ['05_FunctionsGenericsAndUtilityTypes', './practice_ts_05.cjs', 30, 100, '_react_01_typescript_wprowadzenie'],
  ['06_JsxVsTsxHowWeWillLearn', './practice_ts_06.cjs', 30, 100, '_react_01_typescript_wprowadzenie'],
  ['01_AppShellAndFirstComponent', './practice_components_01.cjs', 20, 60, '_react_02_komponenty'],
  ['02_JsxInDepth', './practice_components_02.cjs', 30, 100, '_react_02_komponenty'],
  ['03_ConditionalRendering', './practice_components_03.cjs', 30, 100, '_react_02_komponenty'],
  ['04_PropsBasics', './practice_components_04.cjs', 30, 100, '_react_02_komponenty'],
  ['05_PropsVariantsAndBadges', './practice_components_05.cjs', 30, 100, '_react_02_komponenty'],
  ['06_ListsAndKeys', './practice_components_06.cjs', 30, 100, '_react_02_komponenty'],
  ['07_CompositionAndChildren', './practice_components_07.cjs', 30, 100, '_react_02_komponenty'],
  ['08_PureComponentsAndRenderRules', './practice_components_08.cjs', 30, 100, '_react_02_komponenty'],
  ['09_DrillJsxPropsChildren', './practice_components_09.cjs', 30, 100, '_react_02_komponenty'],
  ['10_BossUserDashboard', './practice_components_10.cjs', 30, 100, '_react_02_komponenty'],
  ['01_CssInReactBasics', './practice_style_01.cjs', 30, 100, '_react_03_stylowanie'],
  ['02_CssModules', './practice_style_02.cjs', 30, 100, '_react_03_stylowanie'],
  ['03_DesignTokensAndTheming', './practice_style_03.cjs', 30, 100, '_react_03_stylowanie'],
  ['04_TailwindCss', './practice_style_04.cjs', 30, 100, '_react_03_stylowanie'],
  ['05_CssInJsOverview', './practice_style_05.cjs', 20, 60, '_react_03_stylowanie'],
  ['06_ComponentLibrariesOverview', './practice_style_06.cjs', 20, 60, '_react_03_stylowanie'],
  ['07_BossStyleGuidePage', './practice_style_07.cjs', 30, 100, '_react_03_stylowanie'],
  ['01_EventsInReact', './practice_state_01.cjs', 30, 100, '_react_04_state_i_tabela'],
  ['02_UseStateBasics', './practice_state_02.cjs', 30, 100, '_react_04_state_i_tabela'],
  ['03_StateAsSnapshotAndBatching', './practice_state_03.cjs', 30, 100, '_react_04_state_i_tabela'],
  ['04_UpdatingObjectsAndArrays', './practice_state_04.cjs', 30, 100, '_react_04_state_i_tabela'],
  ['05_ControlledSearchInput', './practice_state_05.cjs', 30, 100, '_react_04_state_i_tabela'],
  ['06_DerivedDataFiltering', './practice_state_06.cjs', 30, 100, '_react_04_state_i_tabela'],
  ['07_SortableColumns', './practice_state_07.cjs', 30, 100, '_react_04_state_i_tabela'],
  ['08_LocalPagination', './practice_state_08.cjs', 30, 100, '_react_04_state_i_tabela'],
  ['09_DrillEventsAndUseState', './practice_state_09.cjs', 30, 100, '_react_04_state_i_tabela'],
  ['10_DrillImmutableUpdatesAndDerivedData', './practice_state_10.cjs', 30, 100, '_react_04_state_i_tabela'],
  ['11_BossProductCatalog', './practice_state_11.cjs', 30, 100, '_react_04_state_i_tabela'],
  ['01_FirstControlledForm', './practice_form_01.cjs', 30, 100, '_react_05_formularze'],
  ['02_SelectCheckboxRadioTextarea', './practice_form_02.cjs', 30, 100, '_react_05_formularze'],
  ['03_UncontrolledInputsAndFormData', './practice_form_03.cjs', 30, 100, '_react_05_formularze'],
  ['04_ValidationAndErrors', './practice_form_04.cjs', 30, 100, '_react_05_formularze'],
  ['05_SubmitAndReset', './practice_form_05.cjs', 30, 100, '_react_05_formularze'],
  ['06_EditFormAndStateReset', './practice_form_06.cjs', 30, 100, '_react_05_formularze'],
  ['07_ReactHookForm', './practice_form_07.cjs', 30, 100, '_react_05_formularze'],
  ['08_SchemaValidationWithZod', './practice_form_08.cjs', 30, 100, '_react_05_formularze'],
  ['09_FormActionsReact19', './practice_form_09.cjs', 30, 100, '_react_05_formularze'],
  ['10_DrillControlledFields', './practice_form_10.cjs', 30, 100, '_react_05_formularze'],
  ['11_BossRegistrationForm', './practice_form_11.cjs', 30, 100, '_react_05_formularze'],
  ['01_ThinkingInReact', './practice_arch_01.cjs', 30, 100, '_react_06_architektura_stanu'],
  ['02_LiftingStateUp', './practice_arch_02.cjs', 30, 100, '_react_06_architektura_stanu'],
  ['03_SingleSourceOfTruth', './practice_arch_03.cjs', 30, 100, '_react_06_architektura_stanu'],
  ['04_PreservingAndResettingState', './practice_arch_04.cjs', 30, 100, '_react_06_architektura_stanu'],
  ['05_UseReducer', './practice_arch_05.cjs', 30, 100, '_react_06_architektura_stanu'],
  ['06_ContextForPreferences', './practice_arch_06.cjs', 30, 100, '_react_06_architektura_stanu'],
  ['07_ContextWithReducerAtScale', './practice_arch_07.cjs', 30, 100, '_react_06_architektura_stanu'],
  ['08_CustomHooks', './practice_arch_08.cjs', 30, 100, '_react_06_architektura_stanu'],
  ['09_DrillLiftingStateKeyAndReducer', './practice_arch_09.cjs', 30, 100, '_react_06_architektura_stanu'],
  ['10_DrillContextAndCustomHooks', './practice_arch_10.cjs', 30, 100, '_react_06_architektura_stanu'],
  ['11_BossBulkOrderActions', './practice_arch_11.cjs', 30, 100, '_react_06_architektura_stanu'],
  ['01_ComponentLifecycleMentalModel', './practice_effect_01.cjs', 30, 100, '_react_07_efekty'],
  ['02_UseEffectSynchronization', './practice_effect_02.cjs', 30, 100, '_react_07_efekty'],
  ['03_DependenciesAndStaleClosures', './practice_effect_03.cjs', 30, 100, '_react_07_efekty'],
  ['04_CleanupAndStrictMode', './practice_effect_04.cjs', 30, 100, '_react_07_efekty'],
  ['05_YouMightNotNeedAnEffect', './practice_effect_05.cjs', 30, 100, '_react_07_efekty'],
  ['06_UseEffectEventAndUseLayoutEffect', './practice_effect_06.cjs', 30, 100, '_react_07_efekty'],
  ['07_UseSyncExternalStore', './practice_effect_07.cjs', 30, 100, '_react_07_efekty'],
  ['08_DrillUseEffect', './practice_effect_08.cjs', 30, 100, '_react_07_efekty'],
  ['09_BossEffectRefactor', './practice_effect_09.cjs', 30, 100, '_react_07_efekty'],
  ['01_RefsBasics', './practice_ref_01.cjs', 30, 100, '_react_08_refs_i_wzorce_ui'],
  ['02_RefsToDomElements', './practice_ref_02.cjs', 30, 100, '_react_08_refs_i_wzorce_ui'],
  ['03_UseImperativeHandle', './practice_ref_03.cjs', 30, 100, '_react_08_refs_i_wzorce_ui'],
  ['04_TabsComponent', './practice_ref_04.cjs', 30, 100, '_react_08_refs_i_wzorce_ui'],
  ['05_PortalAndModal', './practice_ref_05.cjs', 30, 100, '_react_08_refs_i_wzorce_ui'],
  ['06_FocusManagementAndKeyboard', './practice_ref_06.cjs', 30, 100, '_react_08_refs_i_wzorce_ui'],
  ['07_ToastAndUndo', './practice_ref_07.cjs', 30, 100, '_react_08_refs_i_wzorce_ui'],
  ['08_DropdownsPopoversAndTooltips', './practice_ref_08.cjs', 30, 100, '_react_08_refs_i_wzorce_ui'],
  ['09_DrillRefsAndPortals', './practice_ref_09.cjs', 30, 100, '_react_08_refs_i_wzorce_ui'],
  ['10_BossAccessibleProfileModal', './practice_ref_10.cjs', 30, 100, '_react_08_refs_i_wzorce_ui'],
  ['01_CompoundComponents', './practice_pattern_01.cjs', 30, 100, '_react_09_wzorce_komponentow'],
  ['02_RenderPropsAndHocs', './practice_pattern_02.cjs', 30, 100, '_react_09_wzorce_komponentow'],
  ['03_ControlledAndUncontrolledApis', './practice_pattern_03.cjs', 30, 100, '_react_09_wzorce_komponentow'],
  ['04_PolymorphicComponents', './practice_pattern_04.cjs', 30, 100, '_react_09_wzorce_komponentow'],
  ['05_HeadlessComponentsAndHooks', './practice_pattern_05.cjs', 30, 100, '_react_09_wzorce_komponentow'],
  ['06_BossDesignSystemComponents', './practice_pattern_06.cjs', 30, 100, '_react_09_wzorce_komponentow'],
  ['01_HttpAndRestRefresher', './practice_api_01.cjs', 30, 100, '_react_10_api'],
  ['02_MockApiWithMsw', './practice_api_02.cjs', 30, 100, '_react_10_api'],
  ['03_FetchingData', './practice_api_03.cjs', 30, 100, '_react_10_api'],
  ['04_LoadingErrorEmptyStates', './practice_api_04.cjs', 30, 100, '_react_10_api'],
  ['05_CleanupAndRaceConditions', './practice_api_05.cjs', 30, 100, '_react_10_api'],
  ['06_CrudMutations', './practice_api_06.cjs', 30, 100, '_react_10_api'],
  ['07_ApiClientLayerAndErrors', './practice_api_07.cjs', 30, 100, '_react_10_api'],
  ['08_TanStackQueryBasics', './practice_api_08.cjs', 30, 100, '_react_10_api'],
  ['09_TanStackQueryMutations', './practice_api_09.cjs', 30, 100, '_react_10_api'],
  ['10_PaginationAndInfiniteQueries', './practice_api_10.cjs', 30, 100, '_react_10_api'],
  ['11_BossBooksCrud', './practice_api_11.cjs', 30, 100, '_react_10_api'],
  ['01_RestContractWithSpringBoot', './practice_backend_01.cjs', 30, 100, '_react_11_backend_spring'],
  ['02_AuthenticationJwtAndSessions', './practice_backend_02.cjs', 30, 100, '_react_11_backend_spring'],
  ['03_ProtectedRoutesAndRoles', './practice_backend_03.cjs', 30, 100, '_react_11_backend_spring'],
  ['04_FileUploadAndDownload', './practice_backend_04.cjs', 30, 100, '_react_11_backend_spring'],
  ['05_RealTimeWebSocketsAndSse', './practice_backend_05.cjs', 30, 100, '_react_11_backend_spring'],
  ['06_OpenApiGeneratedClient', './practice_backend_06.cjs', 30, 100, '_react_11_backend_spring'],
  ['07_BossFullStackFeature', './practice_backend_07.cjs', 30, 100, '_react_11_backend_spring'],
  ['01_RouterBasics', './practice_route_01.cjs', 30, 100, '_react_12_routing'],
  ['02_LayoutAndNestedRoutes', './practice_route_02.cjs', 30, 100, '_react_12_routing'],
  ['03_RouteParams', './practice_route_03.cjs', 30, 100, '_react_12_routing'],
  ['04_SearchParamsAsState', './practice_route_04.cjs', 30, 100, '_react_12_routing'],
  ['05_DataLoadersAndActions', './practice_route_05.cjs', 30, 100, '_react_12_routing'],
  ['06_NavigationStatesAndPendingUi', './practice_route_06.cjs', 30, 100, '_react_12_routing'],
  ['07_NotFoundAndRouteErrors', './practice_route_07.cjs', 30, 100, '_react_12_routing'],
  ['08_BossProductRoutes', './practice_route_08.cjs', 30, 100, '_react_12_routing'],
  ['01_WhenGlobalStateLibrary', './practice_global_01.cjs', 30, 100, '_react_13_stan_globalny'],
  ['02_Zustand', './practice_global_02.cjs', 30, 100, '_react_13_stan_globalny'],
  ['03_ReduxToolkitBasics', './practice_global_03.cjs', 30, 100, '_react_13_stan_globalny'],
  ['04_ReduxAsyncAndRtkQuery', './practice_global_04.cjs', 30, 100, '_react_13_stan_globalny'],
  ['05_JotaiAndAtomicState', './practice_global_05.cjs', 30, 100, '_react_13_stan_globalny'],
  ['06_BossCartStore', './practice_global_06.cjs', 30, 100, '_react_13_stan_globalny'],
  ['01_TestingStrategy', './practice_test_01.cjs', 30, 100, '_react_14_testy'],
  ['02_VitestBasics', './practice_test_02.cjs', 30, 100, '_react_14_testy'],
  ['03_ReactTestingLibrary', './practice_test_03.cjs', 30, 100, '_react_14_testy'],
];

function build(source, exerciseCount, questionCount) {
  assert.equal(source.exercises.length, exerciseCount, 'Liczba zadań');
  assert.equal(source.quiz.length, questionCount, 'Liczba pytań');
  const exercises = source.exercises.map(row => {
    assert.equal(row.length, 3);
    const [prompt, hint, solution] = row;
    for (const value of row) assert.ok(typeof value === 'string' && value.trim());
    return { prompt, hint, solution };
  });
  // Powtarzalne mieszanie miejsc poprawnej odpowiedzi, z równym rozkładem A–D.
  const positions = Array.from({ length: questionCount }, (_, i) => i % 4);
  let seed = 20261026;
  for (let i = positions.length - 1; i > 0; i--) {
    seed = (Math.imul(seed, 1664525) + 1013904223) >>> 0;
    const j = seed % (i + 1);
    [positions[i], positions[j]] = [positions[j], positions[i]];
  }
  const quiz = source.quiz.map((row, i) => {
    assert.ok(row.length === 6 || row.length === 7);
    for (const value of row) assert.ok(typeof value === 'string' && value.trim());
    const [question, answer, b, c, d, explanation, code] = row;
    const choices = [b, c, d];
    choices.splice(positions[i], 0, answer);
    assert.equal(new Set(choices).size, 4, `Powtórzona opcja: ${question}`);
    return { question, ...(code ? { code } : {}),
      options: Object.fromEntries(choices.map((value, index) => ['ABCD'[index], value])),
      correct: 'ABCD'[positions[i]], explanation };
  });
  assert.equal(new Set(exercises.map(e => e.prompt)).size, exerciseCount, 'Powtórzone zadanie');
  assert.equal(new Set(quiz.map(q => q.question + '\n' + (q.code ?? ''))).size, questionCount, 'Powtórzone pytanie');
  return { exercises, quiz };
}

// Najpierw walidacja wszystkich źródeł; dopiero potem zapis.
const prepared = entries.map(([slug, source, exercises, questions, chapter = '_react_00_wprowadzenie']) => {
  assert.match(chapter, /^_react_\d{2}_[a-z_]+$/);
  assert.match(slug, /^\d{2}_[A-Za-z0-9]+$/);
  const file = path.resolve(__dirname, '../../../src/main/resources/content', chapter, `${slug}.json`);
  const lesson = JSON.parse(fs.readFileSync(file, 'utf8'));
  return { file, lesson: { ...lesson, ...build(require(source), exercises, questions) } };
});
for (const { file, lesson } of prepared) {
  const output = JSON.stringify(lesson, null, 2) + '\n';
  if (fs.readFileSync(file, 'utf8') !== output) fs.writeFileSync(file, output);
  console.log(`${path.basename(file)}: ${lesson.exercises.length} zadań, ${lesson.quiz.length} pytań.`);
}
