package com.example.javaquest.platform.chapter;

import java.util.List;

/**
 * Statyczna kopia listy rozdzialow/lekcji kursu, uzywana do zasilenia bazy danych
 * platformy przy pierwszym starcie (patrz {@link ContentSeeder}).
 *
 * <p>CELOWO jest to WLASNA, oddzielna kopia, a NIE import
 * {@code com.example.javaquest._00_tableOfContents._TableOfContents.ROZDZIALY} - tamten
 * plik jest czescia "podstawy programowej" kursu (patrz EDU_PLATFORM_PLAN.md, sekcja 2)
 * i platforma go nie modyfikuje. Dodatkowo {@code ROZDZIALY} i zagniezdzony rekord
 * {@code Chapter} w tamtym pliku sa pakietowo-prywatne (bez modyfikatora), wiec i tak nie
 * bylyby dostepne z tego pakietu bez zmiany widocznosci w tamtym pliku.
 *
 * <p><b>WAZNE dla przyszlych sesji:</b> gdy w kursie przybywa nowy rozdzial/lekcja w
 * {@code _TableOfContents.java}, trzeba go RECZNIE dopisac TAKZE tutaj (nie ma
 * automatycznej synchronizacji) - to swiadomy kompromis, zeby nie ruszac pliku bedacego
 * podstawa programowa.
 */
public final class ChapterSeedData {

    /**
     * {@code track} - domyslnie {@link CourseTrack#JAVA} (konstruktor 3-argumentowy, uzywany
     * przez wszystkie dotychczasowe rozdzialy kursu Java ponizej, bez zadnych zmian). Rozdzialy
     * kursu JavaScript (drugi blok platformy, 2026-09) uzywaja jawnie konstruktora
     * 4-argumentowego z {@link CourseTrack#JAVASCRIPT}.
     */
    public record ChapterSeed(String slug, String title, List<String> lessonSlugs, CourseTrack track) {
        public ChapterSeed(String slug, String title, List<String> lessonSlugs) {
            this(slug, title, lessonSlugs, CourseTrack.JAVA);
        }
    }

    public static final List<ChapterSeed> CHAPTERS = List.of(

            new ChapterSeed("_01_fundamentals", "Podstawy Javy", List.of(
                    "00_JavaPlatformBasics", "01_Variables", "02_Operators", "03_Conditionals",
                    "04_Loops", "05_Arrays", "06_StringsAndBuilder", "07_DateAndTime",
                    "08_MathOperations", "09_BigNumberTypes", "10_HeapAndStack", "11_TypeCasting",
                    "12_BinaryAndHex", "13_BitwiseOperators", "14_GarbageCollector",
                    "15_RandomAndSecureRandom", "16_Exceptions"
            )),

            new ChapterSeed("_02_oop", "Programowanie obiektowe (OOP)", List.of(
                    "01_ClassesAndObjects", "02_Encapsulation", "03_Constructors", "04_StaticKeyword",
                    "05_Inheritance", "06_Polymorphism", "07_AbstractClasses", "08_Interfaces",
                    "09_AccessModifiers", "10_FinalKeyword", "11_ObjectClass", "12_InnerClasses",
                    "13_Enums", "14_Records", "15_DesignPatterns"
            )),

            new ChapterSeed("_03_collections", "Java - Kolekcje", List.of(
                    "01_ArrayList", "02_Iteration", "03_CollectionMethods", "04_LinkedList",
                    "05_HashSet", "06_TreeSet", "07_Comparator", "08_HashMap", "09_PriorityQueue",
                    "10_StreamsIntro", "11_StreamsCollectors", "12_StreamsTerminal", "13_StreamsAdvanced",
                    "14_Optional", "15_LinkedHashSet", "16_LinkedHashMap", "17_TreeMap", "18_Deque",
                    "19_ComparableVsComparator", "20_ConcurrentCollections", "21_LegacyCollections",
                    "22_Queue", "23_SpecialMaps"
            )),

            new ChapterSeed("_04_io", "Input/Output i praca z plikami", List.of(
                    "01_IOIntroduction", "02_FileReaderWriter", "03_BufferedReaderWriter",
                    "04_BufferedStreams", "05_DataStreams", "06_PrintWriterAndStream", "07_Scanner",
                    "08_FileClass", "09_PathAndPaths", "10_FilesClass", "11_RandomAccessFile",
                    "12_Charset", "13_TryWithResources", "14_FileExceptions", "15_NioChannelsAndBuffers",
                    "16_ObjectSerialization", "17_SerialVersionUID", "18_TransientKeyword",
                    "19_JsonIntro", "20_Gson", "21_Jackson", "22_SerializableVsJson", "23_CSV", "24_ZIP"
            )),

            new ChapterSeed("_05_multithreading", "Multithreading / Watki w Javie", List.of(
                    "01_ThreadsIntroduction", "02_ThreadClass", "03_Runnable",
                    "04_RunnableAnonymousAndLambda", "05_ThreadBasicMethods", "06_ThreadLifecycleAndStates",
                    "07_RaceCondition", "08_VisibilityProblem", "09_Atomicity", "10_ThreadSafety",
                    "11_Synchronized", "12_Monitor", "13_CriticalSection", "14_Volatile",
                    "15_WaitNotifyNotifyAll", "16_SpuriousWakeup", "17_AtomicClasses",
                    "18_LockAndReentrantLock", "19_ReadWriteLock", "20_Synchronizers",
                    "21_ExecutorService", "22_CallableAndFuture", "23_ScheduledExecutorService",
                    "24_ConcurrentCollectionsAndBlockingQueue", "25_Deadlock", "26_Livelock",
                    "27_Starvation", "28_Interrupt", "29_DaemonThreads", "30_ThreadLocal",
                    "31_ForkJoinPool", "32_CompletableFuture", "33_VirtualThreads", "34_ThreadDebugging",
                    "35_SafeThreadTermination", "36_BestPractices", "37_CommonMistakes"
            )),

            new ChapterSeed("_06_networking", "Programowanie sieciowe", List.of(
                    "01_NetworkingIntroduction", "02_InetAddress", "03_Socket", "04_SocketWhois",
                    "05_SocketHttpDownload", "06_ServerSocket", "07_ServerSocketMultithreaded",
                    "08_URL", "09_URLConnection", "10_HttpURLConnection", "11_HttpProtocol",
                    "12_JsonOverNetwork", "13_XmlParsing", "14_HtmlUnit"
            )),

            new ChapterSeed("_07_servlets", "Servlety - Java Web bez Spring Boot", List.of(
                    "01_ServletApiIntroduction", "02_ServletContainers", "03_ServletProjectSetup",
                    "04_HttpServlet", "05_HttpServletRequest", "06_HttpServletResponse", "07_GetAndPost",
                    "08_OtherHttpMethods", "09_FormParameters", "10_Cookies", "11_HttpSession",
                    "12_ServletConfig", "13_ServletContext", "14_Filters", "15_Listeners",
                    "16_ServletAnnotations", "17_ForwardAndRedirect", "18_FileUpload", "19_JSP"
            )),

            new ChapterSeed("_08_sql", "SQL i relacyjne bazy danych", List.of(
                    "01_DatabaseIntroduction", "02_RelationalModel", "03_TableDesign", "04_SqlDataTypes",
                    "05_NullValues", "06_DataConstraints", "07_Normalization", "08_DDL", "09_DML",
                    "10_Select", "11_Filtering", "12_Sorting", "13_Grouping", "14_Joins",
                    "15_SqlRelationships", "16_Subqueries", "17_Views", "18_Indexes", "19_Transactions",
                    "20_TransactionIsolationLevels"
            )),

            new ChapterSeed("_09_jdbc", "JDBC - Java Database Connectivity", List.of(
                    "01_JdbcIntroduction", "02_JdbcDriver", "03_Connection", "04_Statement",
                    "05_PreparedStatement", "06_ResultSet", "07_JdbcInsert", "08_JdbcSelect",
                    "09_JdbcUpdate", "10_JdbcDelete", "11_CreateAndDropTableFromJava",
                    "12_TryWithResourcesInJdbc", "13_JdbcExceptions", "14_SqlInjection",
                    "15_JdbcTransactions", "16_BatchProcessing", "17_ResultSetMapping", "18_DomainModel",
                    "19_Dto", "20_Mapper"
            )),

            new ChapterSeed("_10_dao", "DAO i architektura dostepu do danych", List.of(
                    "01_DaoIntroduction", "02_LayeredArchitecture", "03_DaoInterface",
                    "04_JdbcDaoImplementation", "05_CrudInDao", "06_OptionalInDao", "07_ListResultsInDao",
                    "08_OneToManyDao", "09_ManyToManyDao", "10_RepositoryVsDao", "11_ConnectionFactory",
                    "12_DatabaseConfiguration", "13_EnvironmentVariables", "14_ConnectionPool",
                    "15_DataSource", "16_ServiceLayer", "17_TransactionsInServiceLayer",
                    "18_SharedConnectionAcrossDao", "19_UnitOfWork", "20_ErrorHandlingAcrossLayers",
                    "21_ValidationBeforeSave", "22_PaginationInJdbc", "23_DynamicSorting",
                    "24_DynamicFiltering", "25_DatabaseMigrations", "26_TestingDao", "27_SqlLogging",
                    "28_JdbcBestPractices"
            )),

            new ChapterSeed("_11_buildtools", "Build Tools w Javie - Ant, Maven, Gradle", List.of(
                    "01_WhyBuildTools", "02_JavacJavaJarClasspath", "03_AntBasics",
                    "04_AntProjectStructure", "05_AntClasspath", "06_AntTesting", "07_AntPackaging",
                    "08_AntAdvanced", "09_AntIvy", "10_AntDebugging", "11_MavenBasics",
                    "12_MavenDependencies", "13_MavenPlugins", "14_MavenAdvanced",
                    "15_MavenWebAndDatabase", "16_MavenTestingAndCoverage", "17_MavenPublishing",
                    "18_MavenTroubleshootingAndPerformance", "19_GradleBasics", "20_GradleAdvanced",
                    "21_GradleDependencyManagement", "22_GradleTestingAndCoverage",
                    "23_GradlePluginsEcosystem", "24_GradlePublishing",
                    "25_GradleTroubleshootingAndPerformance", "26_AntMavenGradleComparison",
                    "27_BuildMigrations", "28_BuildToolsInPractice",
                    "29_BuildToolsTroubleshooting", "30_CapstoneBuildLab"
            )),

            new ChapterSeed("_12_hibernate", "Hibernate - ORM i JPA w praktyce", List.of(
                    "01_OrmIntroduction", "02_HibernateArchitecture", "03_ProjectSetupAndConfiguration",
                    "04_FirstEntityAndBasicMapping", "05_PrimaryKeyGeneration", "06_CrudOperations",
                    "07_SessionVsEntityManager", "08_Transactions", "09_EmbeddableTypes",
                    "10_EnumsAndAttributeConverters", "11_OneToOneAssociation",
                    "12_OneToManyAndManyToOne", "13_ManyToManyAssociation", "14_CascadeTypes",
                    "15_FetchTypesAndNPlusOne", "16_EntityLifecycle", "17_DirtyCheckingAndFlush",
                    "18_HqlBasics", "19_HqlAdvanced", "20_CriteriaApi", "21_NativeSqlQueries",
                    "22_NamedQueries", "23_FirstLevelCache", "24_SecondLevelCacheAndQueryCache",
                    "25_OptimisticLocking", "26_PessimisticLocking", "27_InheritanceMapping",
                    "28_BeanValidationIntegration", "29_HibernateEnvers", "30_BestPracticesAndCapstone"
            )),

            new ChapterSeed("_13_libraries", "Biblioteki - popularne narzedzia w ekosystemie Javy", List.of(
                    "01_WhyLibraries", "02_ChoosingAndAddingDependencies", "03_LombokBasics",
                    "04_LombokConstructorsAndBuilder", "05_LombokAdvancedAndPitfalls", "06_CommonsLang3",
                    "07_CommonsIO", "08_CommonsCollections4", "09_GuavaImmutableCollections",
                    "10_GuavaMultimapMultisetBiMap", "11_GuavaPreconditionsAndCache", "12_OkHttpBasics",
                    "13_OkHttpAsyncAndInterceptors", "14_OkHttpStreamingAndTesting",
                    "15_WhySlf4jNotSystemOut", "16_LogbackConfiguration",
                    "17_MdcAndLoggingBestPractices", "18_WhyDependencyInjection", "19_GuiceBasics",
                    "20_GuiceAdvancedModulesAndScopes", "21_MapStructBasics",
                    "22_MapStructAdvancedMappings", "23_ApachePoiWritingExcel",
                    "24_ApachePoiReadingAndStyling", "25_JsoupParsingHtml", "26_JsoupAdvancedScraping",
                    "27_CaffeineBasics", "28_CaffeineLoadingAndAsyncCache", "29_PicocliBasics",
                    "30_PicocliSubcommandsAndValidation", "31_SnakeYamlBasics", "32_YamlToObjectMapping"
            )),

            new ChapterSeed("_14_advancedjava", "Zaawansowane mechanizmy jezyka Java", List.of(
                    "01_GenericsIntroduction", "02_GenericClassesAndMethods", "03_BoundedTypes",
                    "04_WildcardsExtendsSuper", "05_VarianceAndPecs", "06_TypeErasure",
                    "07_GenericsBestPracticesAndPitfalls", "08_FunctionalInterfaces", "09_LambdaExpressions",
                    "10_MethodReferences", "11_BuiltInFunctionalInterfaces", "12_Annotations",
                    "13_CustomAnnotations", "14_AnnotationRetentionAndProcessing", "15_ReflectionBasics",
                    "16_ReflectionUseCasesAndRisks", "17_DynamicProxies", "18_MethodHandles",
                    "19_SealedClasses", "20_PatternMatchingInstanceof",
                    "21_PatternMatchingSwitchAndRecordPatterns", "22_SwitchExpressions",
                    "23_VarAndTypeInference", "24_Immutability", "25_DefensiveCopying",
                    "26_ServiceLoaderAndSpi", "27_ModulesJpmsBasics", "28_ModulesAdvanced",
                    "29_AdvancedLanguageBestPractices", "30_CapstoneAdvancedJava"
            )),

            new ChapterSeed("_15_jvm_internals", "JVM, pamiec i wydajnosc", List.of(
                    "01_JdkJreJvmAndSpecification", "02_CompilationAndBytecode", "03_ClassLoadingMechanics",
                    "04_CustomClassLoaders", "05_ClasspathVsModulepath", "06_HeapStackMetaspace",
                    "07_ReferenceTypesAndStringPool", "08_GarbageCollectionFoundations",
                    "09_GarbageCollectorAlgorithms", "10_G1GcDeepDive", "11_LowLatencyCollectors",
                    "12_GcTuningAndLogging", "13_JitCompilerBasics", "14_EscapeAnalysisAndInlining",
                    "15_MemoryLeaksInJava", "16_HeapDumpBasics", "17_ThreadDumpBasics",
                    "18_JavaFlightRecorderBasics", "19_ProfilingBasics",
                    "20_JvmTuningAndBestPracticesCapstone"
            )),

            new ChapterSeed("_16_clean_code", "Clean Code i refactoring", List.of(
                    "01_WhatIsCleanCode", "02_Naming", "03_Comments", "04_MethodsAndFunctions",
                    "05_Formatting", "06_ClassesAndResponsibilities", "07_SingleResponsibilityPrinciple",
                    "08_OpenClosedPrinciple", "09_LiskovSubstitutionPrinciple",
                    "10_InterfaceSegregationPrinciple", "11_DependencyInversionPrinciple",
                    "12_CouplingCohesionAndLawOfDemeter", "13_DryKissYagni", "14_CodeSmells",
                    "15_RefactoringBasics", "16_RefactoringCatalog", "17_ExceptionDesign",
                    "18_NullHandling", "19_ImmutabilityInPractice", "20_StaticAnalysisTools",
                    "21_LegacyCodeAndTechnicalDebt", "22_CodeReviewBestPracticesAndCapstone"
            )),

            new ChapterSeed("_17_architecture", "Architektura aplikacji Java", List.of(
                    "01_WhyArchitectureMatters", "02_ArchitectureDecisionRecords", "03_LayeredArchitecture",
                    "04_ControllerServiceRepository", "05_DomainModelVsAnemicModel",
                    "06_BoundedContextsAndDddLite", "07_DtoEntityMapper", "08_ApiVersioningAndCompatibility",
                    "09_PackageByLayerVsPackageByFeature", "10_DependencyDirection",
                    "11_HexagonalArchitectureIntro", "12_PortsAndAdapters", "13_TransactionBoundaries",
                    "14_CachingArchitecture", "15_ValidationArchitecture", "16_ErrorHandlingArchitecture",
                    "17_ModularMonolith", "18_EventDrivenCommunicationBetweenModules",
                    "19_WhenMicroservicesMakeSense", "20_ArchitectureCapstone"
            )),

            new ChapterSeed("_18_rest_api", "REST API i projektowanie HTTP", List.of(
                    "01_HttpDeepDive", "02_RestIntroduction", "03_ResourcesAndEndpoints",
                    "04_HttpMethods", "05_StatusCodes", "06_RequestResponseBody",
                    "07_ContentNegotiation", "08_JsonApiDesign", "09_PathVariablesAndQueryParams",
                    "10_PaginationSortingFiltering", "11_HttpCachingAndConditionalRequests",
                    "12_ErrorResponseDesign", "13_ValidationErrors", "14_Versioning",
                    "15_Idempotency", "16_RateLimitingAndThrottling", "17_PostmanBasics",
                    "18_OpenApiSwaggerIntro", "19_RestVsRpcVsGraphQL",
                    "20_RestApiBestPracticesAndCapstone"
            )),

            new ChapterSeed("_19_security_basics", "Podstawy bezpieczenstwa aplikacji", List.of(
                    "01_AuthenticationVsAuthorization", "02_PasswordHashing", "03_BCrypt",
                    "04_SessionsAndCookies", "05_JwtIntroduction", "06_OAuth2AndOpenIdConnectIntro",
                    "07_AuthorizationPatternsAndRbac", "08_HttpsTlsBasics", "09_Cors", "10_Csrf",
                    "11_Xss", "12_SecurityHeaders", "13_SqlInjectionDeepDive",
                    "14_InsecureDeserialization", "15_XxeAndXmlExternalEntityAttacks",
                    "16_PathTraversalAndFileUploadSecurity", "17_InputValidation",
                    "18_SecretsManagement", "19_SecureLoggingAndAuditing",
                    "20_DependencyAndSupplyChainSecurity", "21_OwaspTop10OverviewAndCapstone"
            )),

            new ChapterSeed("_20_spring_core", "Spring Core i Dependency Injection", List.of(
                    "01_WhatIsSpring", "02_SpringVersionsAndCompatibilityOverview",
                    "03_InversionOfControl", "04_DependencyInjection",
                    "05_ConfigurationStylesXmlVsJavaVsAnnotations", "06_Bean", "07_ApplicationContext",
                    "08_ComponentServiceRepository", "09_ConfigurationAndBeanAnnotation",
                    "10_ConstructorInjection", "11_FieldInjectionWhyAvoid", "12_QualifierAndPrimary",
                    "13_CircularDependencies", "14_BeanScopes", "15_Profiles",
                    "16_PropertiesAndConfiguration", "17_SpelBasics", "18_LifecycleCallbacks",
                    "19_BeanPostProcessorsAndContainerExtensionPoints", "20_ApplicationEvents",
                    "21_SpringAopFundamentals", "22_SpringAopAdvancedPointcutsAndProxies",
                    "23_SpringCoreCapstone"
            )),

            new ChapterSeed("_21_spring_boot", "Spring Boot", List.of(
                    "01_WhatIsSpringBoot", "02_ProjectSetup", "03_Starters", "04_AutoConfiguration",
                    "05_ApplicationPropertiesYaml", "06_ProfilesInSpringBoot", "07_CommandLineRunner",
                    "08_ConfigurationProperties", "09_DevToolsAndProductivity", "10_LoggingInSpringBoot",
                    "11_ErrorHandlingBasics", "12_SpringBootActuator",
                    "13_ObservabilityMicrometerAndTracing", "14_BuildingExecutableJarAndNativeImage",
                    "15_CustomAutoConfigurationAndStarters", "16_SpringBootCapstone"
            )),

            new ChapterSeed("_22_spring_web", "Spring Web i REST API", List.of(
                    "01_ControllerVsRestController", "02_RequestMappingGetPostPutDelete",
                    "03_PathVariable", "04_RequestParam", "05_RequestBody", "06_ResponseEntity",
                    "07_DtoInRestApi", "08_ValidationWithValid", "09_GlobalExceptionHandler",
                    "10_ErrorResponseDto", "11_ContentNegotiationAndMessageConverters",
                    "12_PaginationInApi", "13_SortingAndFiltering", "14_FileUpload", "15_CorsInSpring",
                    "16_InterceptorsAndWebMvcConfigurer", "17_HttpClientsRestTemplateWebClientRestClient",
                    "18_SwaggerOpenApi", "19_RestApiCapstone"
            )),

            new ChapterSeed("_23_spring_data_jpa", "Spring Data JPA", List.of(
                    "01_WhatIsSpringDataJpa", "02_RepositoryInterfaces", "03_CrudRepositoryJpaRepository",
                    "04_QueryMethods", "05_CustomQueries", "06_PaginationAndSorting",
                    "07_EntityRelationshipsInSpring", "08_TransactionsInSpring",
                    "09_LazyLoadingAndNPlusOne", "10_EntityGraph", "11_Projections", "12_Specifications",
                    "13_Auditing", "14_MigrationsWithFlyway", "15_SpringDataJpaCapstone"
            )),

            new ChapterSeed("_24_spring_security", "Spring Security", List.of(
                    "01_WhatIsSpringSecurity", "02_SecurityFilterChain",
                    "03_SecurityConfigEvolutionOldVsNew", "04_DefaultLogin", "05_UserDetailsService",
                    "06_PasswordEncoder", "07_RolesAndAuthorities", "08_FormLogin",
                    "09_ProtectingEndpoints", "10_MethodSecurity", "11_CustomLoginPage",
                    "12_JwtAuthentication", "13_StatelessSecurity", "14_CorsAndCsrfInSpringSecurity",
                    "15_OAuth2LoginAndResourceServerIntro", "16_SecurityExceptionHandling",
                    "17_SpringSecurityCapstone"
            )),

            new ChapterSeed("_25_unit_testing", "Testy jednostkowe - JUnit i AssertJ", List.of(
                    "01_WhyUnitTests", "02_JUnit5ArchitectureOverview", "03_FirstTestAndBuiltInAssertions",
                    "04_TestLifecycleAnnotations", "05_AssertJIntroduction", "06_AssertJForCollectionsAndMaps",
                    "07_AssertJForExceptionsAndCustomAssertions", "08_ParameterizedTests",
                    "09_NestedAndDisplayNameTests", "10_TestOrderingAndRepeatedTests",
                    "11_ConditionalExecutionAndAssumptions", "12_TestTagsAndFiltering", "13_MockitoBasics",
                    "14_MockitoArgumentMatchersAndCaptors", "15_MockitoAnnotationsAndExtension",
                    "16_TestDoublesTaxonomy", "17_TestingExceptionsAndEdgeCases",
                    "18_TestNamingAndOrganizationBestPractices", "19_CodeCoverageBasics",
                    "20_UnitTestingCapstone"
            )),

            new ChapterSeed("_26_integration_testing", "Testy integracyjne", List.of(
                    "01_WhatIsIntegrationTesting", "02_IntegrationTestChallenges",
                    "03_TestingWithRealDatabaseVsInMemory", "04_TestcontainersIntroduction",
                    "05_TestcontainersWithJdbc", "06_TestcontainersLifecycleAndReuse",
                    "07_WireMockIntroduction", "08_StubbingAndVerifyingHttpCalls",
                    "09_TestingFileSystemAndIO", "10_TestDataManagementStrategies",
                    "11_TestIsolationAndIdempotency", "12_FlakyTestsAndHowToFixThem",
                    "13_ContractTestingIntro", "14_CiCdIntegrationOfTests",
                    "15_IntegrationTestingBestPractices", "16_IntegrationTestingCapstone"
            )),

            new ChapterSeed("_27_spring_test", "Testowanie aplikacji Spring (Spring Test)", List.of(
                    "01_SpringTestModuleOverview", "02_SpringBootTestBasics", "03_WebEnvironmentOptions",
                    "04_TestSlicesConcept", "05_WebMvcTestAndMockMvc", "06_DataJpaTestAndTestEntityManager",
                    "07_JsonTestForSerialization", "08_MockitoBeanAndSpyBean",
                    "09_TestConfigurationAndContextCustomization", "10_ActiveProfilesInTests",
                    "11_TestPropertySourceAndDynamicProperties", "12_TestRestTemplateAndWebTestClient",
                    "13_TestingSecuredEndpoints", "14_TestingTransactionalCodeInSpring",
                    "15_TestcontainersServiceConnection", "16_TestingSchedulingAndAsyncCode",
                    "17_ContextCachingAndTestPerformance", "18_ArchUnitForArchitectureTesting",
                    "19_SpringTestBestPractices", "20_SpringTestCapstone"
            )),

            new ChapterSeed("_28_java_evolution", "Ewolucja Javy: od Java 8 do dzis", List.of(
                    "01_JavaReleaseCadenceAndLtsExplained", "02_Java8LambdasAndFunctionalInterfaces",
                    "03_Java8StreamsAndOptional", "04_Java8DefaultAndStaticInterfaceMethods",
                    "05_Java8NewDateTimeApi", "06_Java9ModuleSystemJpms", "07_Java9SmallerFeatures",
                    "08_Java10LocalVariableTypeInference", "09_Java11LtsStringAndFilesMethods",
                    "10_Java11HttpClient", "11_Java12To13SwitchExpressionsPreview",
                    "12_Java14SwitchExpressionsAndRecordsPreview", "13_Java15TextBlocks",
                    "14_Java16RecordsAndPatternMatchingInstanceof", "15_Java17SealedClasses",
                    "16_Java18UtfDefaultAndSimpleWebServer", "17_Java19To20VirtualThreadsPreview",
                    "18_Java21PatternMatchingSwitchAndRecordPatterns", "19_Java21VirtualThreadsFinalized",
                    "20_Java21SequencedCollections", "21_Java22To23NewFeatures",
                    "22_Java24To25LatestFeatures", "23_ChoosingJavaVersionForNewProjects",
                    "24_JavaEvolutionCapstone"
            )),

            new ChapterSeed("_29_spring_reactive", "Programowanie reaktywne ze Spring WebFlux", List.of(
                    "01_WhyReactive", "02_ReactiveStreamsSpecification", "03_ProjectReactorIntro",
                    "04_MonoBasics", "05_FluxBasics", "06_ReactiveOperators",
                    "07_ErrorHandlingInReactiveStreams", "08_SchedulersAndThreading",
                    "09_WebFluxVsSpringMvc", "10_AnnotatedControllersInWebFlux",
                    "11_FunctionalEndpointsRouterFunction", "12_WebClientDeepDive", "13_R2dbcIntro",
                    "14_ReactiveSecurity", "15_TestingReactiveCodeWithStepVerifier",
                    "16_WhenToUseReactiveVsBlocking", "17_ReactiveCapstone"
            )),

            new ChapterSeed("_30_spring_messaging_and_async", "Asynchronicznosc i komunikaty", List.of(
                    "01_AsyncMethodsWithEnableAsync", "02_TaskExecutorConfiguration",
                    "03_CompletableFutureWithAsync", "04_SchedulingWithEnableScheduling",
                    "05_ApplicationEventsDeepDive", "06_JmsIntro", "07_SpringJmsTemplate",
                    "08_RabbitMqConcepts", "09_SpringAmqpBasics", "10_KafkaConcepts",
                    "11_SpringKafkaBasics", "12_MessageDrivenArchitecturePatterns",
                    "13_ErrorHandlingAndDeadLetterQueues", "14_TestingAsyncAndMessagingCode",
                    "15_ChoosingRabbitVsKafka", "16_MessagingCapstone"
            )),

            new ChapterSeed("_31_spring_cloud_microservices", "Spring Cloud i mikroserwisy", List.of(
                    "01_SpringCloudOverview", "02_ServiceDiscoveryConcepts", "03_SpringCloudNetflixEureka",
                    "04_ConfigServerIntro", "05_SpringCloudConfigClient", "06_ApiGatewayIntro",
                    "07_GatewayRoutingAndFilters", "08_ClientSideLoadBalancing", "09_CircuitBreakerConcepts",
                    "10_Resilience4jIntegration", "11_DistributedTracingIntro",
                    "12_DistributedTracingWithZipkin", "13_DeclarativeHttpClientsWithFeign",
                    "14_SagaPatternIntro", "15_EventDrivenMicroservicesWithSpringCloudStream",
                    "16_SecurityAcrossMicroservices", "17_ContainerizingSpringBootApps",
                    "18_ObservabilityAcrossServices", "19_MicroservicesCapstone"
            )),

            // Etap 4 dopisany po zamknieciu Etapu 4 "migracja rozdzialami" (_01.._31) - nowy
            // rozdzial pokrywajacy luke miedzy tym kursem a raportem rynkowym o rekrutacji
            // (patrz "kytyyczne tematy.md" w katalogu glownym) - zakladka "Krytyczne" na
            // platformie linkuje tu dla tematow algorytmicznych, ktorych kurs wczesniej nie mial.
            new ChapterSeed("_32_algorithms_and_data_structures", "Algorytmy i struktury danych", List.of(
                    "01_BigONotation", "02_ArraysAndStrings", "03_TwoPointers", "04_SlidingWindow",
                    "05_HashMapPatterns", "06_StackAndQueuePatterns", "07_SortingAlgorithms",
                    "08_BinarySearch", "09_Recursion", "10_LinkedListPatterns", "11_BinaryTrees",
                    "12_BreadthFirstSearch", "13_DepthFirstSearch", "14_InterviewStrategy",
                    "15_AlgorithmsCapstone"
            )),

            // Kolejny rozdzial pokrywajacy luke z raportu rynkowego (patrz komentarz przy _32
            // wyzej) - Git to JEDYNY temat oznaczony w critical-topics.json priorytetem "critical"
            // bez przypisanego rozdzialu ("Brak dedykowanego rozdzialu o Git w kursie - kandydat
            // do nowego rozdzialu"). Ten sam lekki format co _32 (14-15 blokow teorii, ale
            // ZMIENNA, MALA liczba exercises/quiz zamiast stalych 30/100).
            new ChapterSeed("_33_git_essentials", "Git - codzienna praca z systemem kontroli wersji", List.of(
                    "01_WhyVersionControl", "02_RepositoryCommitsAndStagingArea", "03_BranchingBasics",
                    "04_MergingAndConflictResolution", "05_Rebase", "06_RemoteRepositories",
                    "07_PullRequestWorkflow", "08_Stash", "09_UndoingChanges", "10_CherryPick",
                    "11_Tags", "12_GitignoreAndAttributes", "13_LogBlameAndBisect",
                    "14_CommitConventionsAndBranchingStrategies", "15_GitHooks", "16_GitWorkflowCapstone"
            )),

            // Trzeci rozdzial dopisany po zamknieciu Etapu 4 (po _32, _33) - pokrywa
            // "tools-docker-basics" w critical-topics.json: temat priorytetu "critical", ktory MIAL
            // przypisany rozdzial (_31_spring_cloud_microservices/17_ContainerizingSpringBootApps),
            // ale z notatka wprost mowiaca, ze kurs uczy Dockera TYLKO w kontekscie konteneryzacji
            // mikroserwisow (multi-stage build) i "warto dodac osobny, wprowadzajacy rozdzial z
            // docker run/volumes/networks" - dokladnie to pokrywa ten rozdzial. SWIADOMIE NIE
            // obejmuje Docker Compose/Kubernetes - to osobne, nizej priorytetowe tematy w
            // critical-topics.json ("very-important"/"important"), nie czesc tego zadania.
            new ChapterSeed("_34_docker_fundamentals", "Docker - podstawy konteneryzacji", List.of(
                    "01_WhyContainers", "02_ImagesAndContainers", "03_DockerfileBasics",
                    "04_RunningContainers", "05_ImageLayersAndCaching", "06_EnvironmentAndConfiguration",
                    "07_Volumes", "08_DockerNetworks", "09_MultiStageBuilds",
                    "10_DockerfileBestPracticesAndSecurity", "11_DockerFundamentalsCapstone"
            )),

            // ===== PUSTE ROZDZIALY (PLANOWANIE) - dodane 2026-09-15, TRESC CELOWO NIE ISTNIEJE =====
            // Na wyrazna prosbe uzytkownika: rozplanowanie (same nazwy lekcji, bez tresci JSON)
            // WSZYSTKICH pozostalych tematow z critical-topics.json oznaczonych jako
            // "do opracowania" (priorytet "very-important"/"important" - WSZYSTKIE tematy
            // "critical" byly juz w 100% pokryte PRZED dodaniem tych rozdzialow, patrz _32/_33/_34
            // wyzej). Kazdy z tych rozdzialow bedzie POKAZYWAL sie w nawigacji, ale KAZDA jego
            // lekcja wyswietli sie jako "tresc w przygotowaniu" (LessonContentLoader pomija lekcje
            // bez odpowiadajacego pliku JSON pod src/main/resources/content/<rozdzial>/ - to
            // CELOWE, udokumentowane zachowanie Fazy 1, patrz komentarz w LessonContentLoader.java)
            // - TRESC do napisania w KOLEJNEJ, oddzielnej turze pracy, rozdzial po rozdziale, tym
            // samym wzorcem co _32/_33/_34 (14-15 blokow teorii, zmienna mala liczba
            // exercises/quiz). critical-topics.json NIE zostal jeszcze zaktualizowany dla tych
            // tematow (zostaja "do opracowania"), zeby NIE pokazywac przedwczesnie "w kursie" dla
            // pustych lekcji - flip nastapi rozdzial po rozdziale, w miare pisania tresci.
            //
            // SWIADOMIE POMINIETE (na wyrazna prosbe uzytkownika, "wywalic calkowicie"):
            // other-react, other-angular - USUNIETE z critical-topics.json, nie beda mialy
            // zadnego rozdzialu (poza zakresem kursu backendowego, raport sam odradza).
            //
            // "tools-linux" TEZ SWIADOMIE POMINIETY (na wyrazna prosbe uzytkownika, "to bedzie
            // zupelnie co innego") - Linux/terminal to ODREBNY temat, celowo NIE dodany do tej
            // partii rozdzialow, mimo ze byl pierwotnie zaplanowany jako _35_linux_fundamentals -
            // wpis w critical-topics.json ("tools-linux") zostaje "do opracowania" bez przypisania.
            //
            // "other-nosql" jest CELOWO najkrotszy (3 lekcje) - uzytkownik zastrzegl "o nosql
            // mozna jedynie zachaczyc z grubsza" (tylko ogolna swiadomosc, nie pelny rozdzial).

            // Pokrywa "tools-docker-compose" (very-important, "Kurs pokazuje Dockerfile, ale nie
            // docker-compose.yml dla wielu serwisow (app+DB+broker) - kandydat do nowego
            // rozdzialu"). Naturalne przedluzenie _34_docker_fundamentals.
            new ChapterSeed("_35_docker_compose", "Docker Compose - aplikacje wielokontenerowe", List.of(
                    "01_WhyDockerCompose", "02_ComposeFileBasics",
                    "03_ServicesNetworksAndVolumesInCompose", "04_EnvironmentAndEnvFiles",
                    "05_DependsOnAndStartupOrder", "06_ComposeCommandsAndWorkflow",
                    "07_MultiContainerJavaQuestExample", "08_DockerComposeCapstone"
            )),

            // Pokrywa "cloud-kubernetes" (important, "swiadomie na niskim priorytecie, raport
            // odradza glebsze wejscie na tym etapie kariery") - stad CELOWO tylko 6 lekcji,
            // sama swiadomosc/podstawy, nie glebokie kompetencje operacyjne.
            new ChapterSeed("_36_kubernetes_fundamentals", "Kubernetes - świadomość podstaw", List.of(
                    "01_WhyKubernetes", "02_PodsAndDeployments", "03_ServicesAndNetworkingBasics",
                    "04_ConfigMapsAndSecrets", "05_KubectlBasics",
                    "06_KubernetesVsDockerComposeCapstone"
            )),

            // Pokrywa "cloud-aws" (important, "niski priorytet, wybierz jednego dostawce" - AWS
            // wybrany jako ten jeden dostawca, zgodnie z rekomendacja raportu).
            new ChapterSeed("_37_cloud_aws_fundamentals", "AWS - podstawy chmury obliczeniowej", List.of(
                    "01_WhyCloudAndWhyAws", "02_CoreConceptsRegionsAndIam", "03_Ec2Basics",
                    "04_S3Basics", "05_RdsBasics", "06_VpcBasicsAndNetworking",
                    "07_CloudWatchAndSecretsManagement", "08_DeployingSpringBootToAws",
                    "09_AwsFundamentalsCapstone"
            )),

            // Pokrywa "other-redis" (important, "Kurs uczy cache lokalnego (Caffeine,
            // _13_libraries), ale nie Redis jako wspoldzielonego cache").
            new ChapterSeed("_38_redis_and_caching", "Redis i cache współdzielony", List.of(
                    "01_WhyDistributedCache", "02_RedisDataStructures",
                    "03_RunningRedisAndBasicCommands", "04_SpringDataRedisIntegration",
                    "05_CacheInvalidationStrategies", "06_RedisPubSubAwareness",
                    "07_RedisCachingCapstone", "08_ConnectingSafelyWithRedisCli",
                    "09_KeyspaceDiscoveryAndLogicalSchema", "10_InspectingValuesTypesTtlAndMemory",
                    "11_DesigningCsvToRedisMapping", "12_ImportingCsvAsStringsAndHashes",
                    "13_ModelingRelationshipsWithSetsAndSortedSets", "14_BulkLoadingWithRespAndPipe",
                    "15_ProductionSafeVerificationAndRollback", "16_CsvToRedisMigrationCapstone"
            )),

            // Pokrywa "observability-prometheus-grafana" (important, "Actuator/Micrometer sa
            // omowione, ale nie konkretnie Prometheus/Grafana jako stos wizualizacji").
            new ChapterSeed("_39_observability_prometheus_grafana", "Prometheus i Grafana - wizualizacja metryk", List.of(
                    "01_WhyMetricsVisualization", "02_PrometheusDataModelAndPromQL",
                    "03_MicrometerToPrometheusIntegration", "04_RunningPrometheusLocally",
                    "05_GrafanaDashboardsBasics", "06_AlertingBasics", "07_ObservabilityCapstone"
            )),

            // Pokrywa "testing-rest-assured" (important, "Kurs testuje REST przez
            // MockMvc/WebTestClient - REST Assured jako osobna biblioteka nie jest omowiona").
            // CELOWO krotszy (5 lekcji) - buduje na juz istniejacej wiedzy testowej z _25/_26/_27.
            new ChapterSeed("_40_rest_assured_testing", "REST Assured - testowanie API", List.of(
                    "01_WhyRestAssured", "02_FirstRestAssuredTest",
                    "03_RequestSpecificationAndAuth", "04_ResponseValidationAndJsonPath",
                    "05_RestAssuredWithSpringBootCapstone"
            )),

            // Pokrywa "other-nosql" (important, "Kurs jest w pelni relacyjny (SQL/JPA) - brak
            // wprowadzenia do NoSQL"). CELOWO NAJKROTSZY rozdzial (3 lekcje) - na wyrazna prosbe
            // uzytkownika ma to byc WYLACZNIE ogolna swiadomosc/orientacja, NIE pelny kurs NoSQL.
            new ChapterSeed("_41_nosql_overview", "NoSQL - świadomość podstaw", List.of(
                    "01_RelationalVsNoSql", "02_DocumentStoresAndMongoDbAwareness",
                    "03_ColumnAndKeyValueStoresAwareness"
            )),

            // ===== DRUGI BLOK PLATFORMY: KURS JAVASCRIPT (2026-09) =====
            // Struktura/kolejnosc rozdzialow i lekcji ZAPROJEKTOWANA na podstawie gotowego
            // materialu zrodlowego w "dodatkowe materialy/kurs js/js-course/" (14 folderow
            // 01-14, kazdy z plikami lekcja.js/cwiczenia.js/dodatkowe.js, patrz README w
            // "00-spis-tresci"). KAZDY znacznik "JS-XXX" w danym "lekcja.js" odpowiada JEDNEJ
            // lekcji platformy (90 znacznikow lacznie = 90 lekcji w 14 rozdzialach) - TA SAMA
            // ziarnistosc, co "JS_COURSE_STAGE_PROMPT.md" (jedyne, obowiazujace zrodlo instrukcji
            // dla PISANIA tresci tych lekcji, patrz plik w katalogu glownym repo). Rozdzialy
            // NA RAZIE PUSTE (bez plikow tresci JSON pod src/main/resources/content/) - TA SAMA,
            // udokumentowana konwencja "w przygotowaniu", co przy scaffoldingu _35-_41 wyzej -
            // TRESC do napisania w KOLEJNEJ turze pracy, rozdzial po rozdziale.
            new ChapterSeed("_js_01_zmienne", "JavaScript - zmienne i zasięgi", List.of(
                    "01_VarKeyword", "02_Hoisting", "03_LetKeyword", "04_ConstKeyword",
                    "05_VariableScopes", "06_VarLetConstWhenToUse"
            ), CourseTrack.JAVASCRIPT),

            new ChapterSeed("_js_02_typy_danych", "JavaScript - typy danych", List.of(
                    "01_StringBasics", "02_StringMethods", "03_NumberAndArithmetic", "04_BigInt",
                    "05_BooleanUndefinedNull", "06_Symbol", "07_ValueVsReferenceTypes"
            ), CourseTrack.JAVASCRIPT),

            new ChapterSeed("_js_03_funkcje", "JavaScript - funkcje", List.of(
                    "01_FunctionDeclarationVsExpression", "02_ArrowFunctions", "03_AnonymousFunctions",
                    "04_CallbackAndPredicateFunctions", "05_Closures"
            ), CourseTrack.JAVASCRIPT),

            new ChapterSeed("_js_04_tablice", "JavaScript - tablice", List.of(
                    "01_ArrayBasicsAndMutatingMethods", "02_FilterMethod", "03_MapMethod",
                    "04_ForEachMethod", "05_SortMethodInDepth", "06_FlatMapAndFlat",
                    "07_ArrayDestructuring", "08_ArraySpreadOperator"
            ), CourseTrack.JAVASCRIPT),

            new ChapterSeed("_js_05_obiekty", "JavaScript - obiekty", List.of(
                    "01_ObjectBasics", "02_ObjectStaticMethods", "03_ObjectDestructuring",
                    "04_OptionalChainingAndNullishCoalescing", "05_ObjectSpreadOperator",
                    "06_JsonStringifyAndParse", "07_DateObject"
            ), CourseTrack.JAVASCRIPT),

            new ChapterSeed("_js_06_kontrola_przeplywu", "JavaScript - sterowanie przepływem", List.of(
                    "01_IfElseAndTernary", "02_SwitchAndObjectAsMap", "03_ForForOfForIn",
                    "04_TryCatchFinally", "05_LooseVsStrictEquality"
            ), CourseTrack.JAVASCRIPT),

            new ChapterSeed("_js_07_this_i_konteksty", "JavaScript - this i konteksty wywołania", List.of(
                    "01_ThisBasics", "02_ThisLosingContext", "03_ArrowFunctionsAndThis",
                    "04_CallMethod", "05_ApplyMethod", "06_BindMethod",
                    "07_ThisInClassesAndEventHandlers"
            ), CourseTrack.JAVASCRIPT),

            new ChapterSeed("_js_08_klasy", "JavaScript - klasy", List.of(
                    "01_ClassConstructorAndMethods", "02_ClassFields", "03_GettersAndSetters",
                    "04_StaticFieldsAndMethods", "05_InheritanceExtendsSuper",
                    "06_PrivateFieldsAndMethods", "07_ClassVsFactoryFunction"
            ), CourseTrack.JAVASCRIPT),

            new ChapterSeed("_js_09_kolekcje", "JavaScript - kolekcje Map i Set", List.of(
                    "01_SetUniqueValues", "02_MapKeyValueWithAnyKeyType", "03_IteratingOverMap",
                    "04_MapObjectSetArrayConversions", "05_WhenToUseMapSetVsObjectArray",
                    "06_WeakMapAndWeakSet"
            ), CourseTrack.JAVASCRIPT),

            new ChapterSeed("_js_10_moduly", "JavaScript - moduły ES", List.of(
                    "01_WhatAreEsModules", "02_NamedImport", "03_DefaultImport",
                    "04_CombiningNamedAndDefaultImport", "05_NamespaceImport", "06_DynamicImport",
                    "07_ModulesAndReactAnalogy"
            ), CourseTrack.JAVASCRIPT),

            new ChapterSeed("_js_11_dom", "JavaScript - DOM", List.of(
                    "01_WhatIsTheDom", "02_SelectingDomElements", "03_ManipulatingElementContent",
                    "04_CreatingAndRemovingElements", "05_AttributesAndDataAttributes",
                    "06_ClassListDynamicClasses", "07_ChangingCssStylesViaJs"
            ), CourseTrack.JAVASCRIPT),

            new ChapterSeed("_js_12_zdarzenia", "JavaScript - zdarzenia", List.of(
                    "01_AddEventListener", "02_EventObjectTargetPreventDefaultStopPropagation",
                    "03_MouseEvents", "04_KeyboardAndFormEvents",
                    "05_EventPropagationAndDelegation", "06_RegularExpressions"
            ), CourseTrack.JAVASCRIPT),

            new ChapterSeed("_js_13_asynchronicznosc", "JavaScript - asynchroniczność", List.of(
                    "01_SetTimeoutAndSetInterval", "02_Promises", "03_AsyncAwait", "04_FetchApi",
                    "05_FormDataAndSendingData", "06_RestApiPatterns"
            ), CourseTrack.JAVASCRIPT),

            new ChapterSeed("_js_14_srodowisko_przegladarkowe", "JavaScript - środowisko przeglądarkowe", List.of(
                    "01_WindowObject", "02_LocalStorageAndSessionStorage", "03_WindowLocation",
                    "04_WindowHistoryAndHistoryApi", "05_EcmaScriptStandards", "06_DebuggingInVsCode"
            ), CourseTrack.JAVASCRIPT),

            // ===== TRZECI BLOK PLATFORMY: KURS LINUX (2026-09-26) =====
            // Struktura ZAPROJEKTOWANA na podstawie notatek w "dodatkowe materialy/LinuxMistery/"
            // (klasy LN01-LN31 w src/main/java/org/example/notes - kazda notatka to temat z
            // ponumerowanymi sekcjami). Rozdzial = grupa tematycznie powiazanych notatek, lekcja =
            // jedna sekcja lub kilka powiazanych sekcji notatki. Mapowanie lekcja -> sekcje zrodlowe
            // jest w LINUX_COURSE_STAGE_PROMPT.md (katalog glowny repo). Pliki tresci JSON istnieja,
            // ale sa PUSTE ({"theory": [], "exercises": [], "quiz": []}) - frontend pokazuje je jako
            // "w przygotowaniu". Kurs jest CELOWO szerszy niz notatki (46 rozdzialow, 437 lekcji, 4 czesci):
            // 234 lekcje maja zrodlo w notatkach, pozostale to uzupelnienia do pelnego programu
            // (poziom LPIC-1/RHCSA + praktyka serwerowa programisty Javy) - oznaczone w tabeli.
            // --- CZESC I. Fundamenty i praca w terminalu ---
            new ChapterSeed("_lx_01_wprowadzenie", "Linux - wprowadzenie, historia i dystrybucje", List.of(
                    "01_WhatIsLinux", "02_UnixAndLinuxHistory", "03_KernelVsDistribution",
                    "04_DistributionFamilies", "05_ReleaseCyclesAndLts", "06_DesktopEnvironmentsVsServers",
                    "07_OpenSourceLicenses", "08_LinuxInJavaDeveloperWork"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_02_wirtualizacja", "Linux - wirtualizacja i środowisko do nauki", List.of(
                    "01_WhatIsVirtualization", "02_HypervisorTypes", "03_VmwareAndVirtualBox",
                    "04_VirtualizationAndCpuArchitecture", "05_InstallingLinuxInVm",
                    "06_VirtualDiskFilesAndReadyMachines", "07_VmResourcesRamAndCpu",
                    "08_ResizingVirtualDisks", "09_SnapshotsAndCloning", "10_GuestAdditionsAndSharedFolders"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_03_terminal_i_powloka", "Linux - terminal i powłoka: pierwsze kroki", List.of(
                    "01_TerminalShellAndConsole", "02_PromptAndCommandStructure", "03_GettingHelpManAndHelp",
                    "04_WhatisAproposAndTldr", "05_TypeWhichAndWhereis", "06_BuiltinsVsExternalCommands",
                    "07_CommandHistory", "08_ReadlineKeyboardShortcuts", "09_TabCompletion",
                    "10_ExitStatusAndCommandChaining", "11_AliasesBasics"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_04_hierarchia_katalogow", "Linux - hierarchia katalogów i ścieżki", List.of(
                    "01_EverythingIsAFile", "02_RootDirectoryAndFhs", "03_AbsoluteAndRelativePaths",
                    "04_BinSbinLibAndUsr", "05_EtcHomeAndRoot", "06_BootDevProcAndSys", "07_VarTmpOptAndMnt",
                    "08_UsrMergeAndModernLayout"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_05_operacje_na_plikach", "Linux - operacje na plikach i katalogach", List.of(
                    "01_LsListingFiles", "02_LsLongFormatExplained", "03_CdAndPwdNavigation",
                    "04_HiddenFilesAndDotfiles", "05_TouchAndTimestamps", "06_MkdirAndRmdir", "07_CpCopying",
                    "08_MvMovingAndRenaming", "09_RmDeletingSafely", "10_GlobbingWildcards",
                    "11_BraceExpansion", "12_HardAndSymbolicLinks", "13_StatAndFileMetadata",
                    "14_FileTypeDetection", "15_TreeDuAndDf"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_06_przegladanie_tekstu", "Linux - przeglądanie i proste przetwarzanie tekstu", List.of(
                    "01_CatTacAndNl", "02_LessAndMore", "03_HeadAndTail", "04_WcCounting", "05_SortSorting",
                    "06_UniqDuplicates", "07_CutColumns", "08_PasteAndJoin", "09_TrTranslatingCharacters",
                    "10_TeeSplittingOutput", "11_DiffCommAndCmp", "12_ColumnAndPrintf",
                    "13_EncodingsAndLineEndings"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_07_strumienie_i_potoki", "Linux - strumienie, przekierowania i potoki", List.of(
                    "01_StdinStdoutStderr", "02_FileDescriptors", "03_OutputRedirection",
                    "04_ErrorRedirection", "05_InputRedirectionAndHereDoc", "06_HereStrings", "07_Pipes",
                    "08_DevNullAndPracticalScenarios", "09_CommandSubstitution", "10_XargsBuildingCommands",
                    "11_ProcessSubstitution", "12_NamedPipesFifo"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_08_edytory_nano_i_vim", "Linux - edytory: nano i Vim od podstaw", List.of(
                    "01_NanoEditor", "02_StartingVimAndModes", "03_SavingAndQuitting",
                    "04_MovingAroundTheDocument", "05_DeletingText", "06_YankAndPaste", "07_ReplacingText",
                    "08_UndoRedoAndRepeat", "09_SearchingInVim", "10_EverydayWorkflow"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_09_vim_zaawansowany", "Linux - Vim zaawansowany", List.of(
                    "01_OperatorsAndMotions", "02_TextObjects", "03_VisualAndBlockMode",
                    "04_SubstituteWithRegex", "05_RegistersAndClipboard", "06_Macros",
                    "07_BuffersWindowsAndTabs", "08_VimrcConfiguration", "09_PluginsWithPathogen",
                    "10_NativePackagesAndPopularPlugins"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_10_wyszukiwanie_plikow", "Linux - wyszukiwanie plików", List.of(
                    "01_FindByName", "02_FindWithRegex", "03_FindByType", "04_FindBySize",
                    "05_FindByModificationTime", "06_FindByPermissionsAndOwner",
                    "07_FindLogicalOperatorsAndPrune", "08_FindExec", "09_FindWithXargs", "10_LocateAndIndex"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_11_grep_i_regex", "Linux - grep i wyrażenia regularne", List.of(
                    "01_GrepBasics", "02_GrepOptions", "03_GrepRecursiveAndMultipleFiles",
                    "04_GrepContextLines", "05_GrepWithPipesAndLogs", "06_RegexLiteralsDotAndAnchors",
                    "07_RegexCharacterClasses", "08_RegexQuantifiers", "09_RegexAlternationAndGroups",
                    "10_BackreferencesAndWordBoundaries", "11_BreEreAndPcre", "12_GrepFixedStrings",
                    "13_RegexPracticeOnRealFiles", "14_RipgrepModernAlternative"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_12_sed", "Linux - sed: edytor strumieniowy", List.of(
                    "01_WhatIsSed", "02_SubstituteCommand", "03_AddressesAndRanges",
                    "04_DeletePrintInsertAppend", "05_InPlaceEditing", "06_SedRegexGroups",
                    "07_MultipleCommandsAndScripts", "08_EditingConfigFilesWithSed"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_13_awk", "Linux - awk: przetwarzanie kolumn i raporty", List.of(
                    "01_AwkFieldsAndSyntax", "02_FieldSeparatorsFsAndOfs", "03_AwkPatternsAndConditions",
                    "04_AwkWithPipes", "05_AwkBeginAndEnd", "06_AwkVariablesAndAggregation", "07_AwkRegex",
                    "08_AwkAssociativeArrays", "09_AwkPrintfAndBuiltinFunctions", "10_AwkReportsFromLogs",
                    "11_GrepSedOrAwk"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_14_archiwizacja", "Linux - archiwizacja i kompresja", List.of(
                    "01_GzipAndGunzip", "02_TarArchives", "03_ListingAndExtractingTar", "04_TarGzCombined",
                    "05_StreamingZcatAndTar", "06_Bzip2XzAndZstd", "07_ZipAndUnzip",
                    "08_ArchivingBestPractices"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_15_wget_i_curl", "Linux - pobieranie danych i HTTP: wget i curl", List.of(
                    "01_WgetDownloadingFiles", "02_WgetResumeAndRecursive", "03_CurlBasics",
                    "04_CurlMethodsHeadersAndBody", "05_CurlTestingRestApis", "06_ChecksumsSha256"
            ), CourseTrack.LINUX),

            // --- CZESC II. Administracja systemem ---
            new ChapterSeed("_lx_16_uzytkownicy_i_grupy", "Linux - użytkownicy i grupy", List.of(
                    "01_UsersUidsAndSystemAccounts", "02_EtcPasswd", "03_EtcShadow", "04_GroupsAndEtcGroup",
                    "05_CreatingUsersUseradd", "06_ModifyingAndDeletingUsers", "07_PasswordsAndAging",
                    "08_LoginProcess", "09_ShellsAndNologin", "10_SuAndSudo", "11_SudoersConfiguration",
                    "12_WhoIsLoggedIn"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_17_uprawnienia", "Linux - prawa dostępu", List.of(
                    "01_ReadingPermissions", "02_ChmodNumeric", "03_ChmodSymbolic",
                    "04_FileVsDirectoryPermissions", "05_UmaskDefaultPermissions", "06_ChownAndChgrp",
                    "07_SuidSgidAndStickyBit", "08_AccessControlLists", "09_FileAttributesChattr",
                    "10_PermissionTroubleshooting"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_18_pakiety_debian", "Linux - pakiety w Debianie i Ubuntu (APT, dpkg)", List.of(
                    "01_WhatIsApt", "02_UpdateVsUpgrade", "03_RepositoriesAndSourcesList",
                    "04_SearchingAndInstallingPackages", "05_ListingAndInspectingPackages",
                    "06_DependenciesExplained", "07_RemovePurgeAndAutoremove", "08_DebPackagesAndDpkg",
                    "09_ThirdPartyReposAndGpgKeys", "10_HoldingAndPinningVersions", "11_UnattendedUpgrades"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_19_pakiety_inne_dystrybucje", "Linux - pakiety w innych dystrybucjach i formaty uniwersalne", List.of(
                    "01_DnfAndYum", "02_RpmPackages", "03_ApkInAlpine", "04_PacmanInArch", "05_SnapAndFlatpak",
                    "06_SdkmanForJavaTools"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_20_kompilacja_ze_zrodel", "Linux - kompilacja ze źródeł i biblioteki", List.of(
                    "01_WhyBuildFromSource", "02_BuildToolchainGccAndMake", "03_ConfigureMakeInstall",
                    "04_SharedLibrariesSo", "05_LddAndLdconfig", "06_ApacheModulesAndApxs",
                    "07_UninstallingSourceBuilds"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_21_procesy", "Linux - procesy i sygnały", List.of(
                    "01_WhatIsAProcess", "02_CpuScheduler", "03_PsCommand", "04_ParentAndChildProcesses",
                    "05_ProcFilesystemForProcesses", "06_TopAndHtop", "07_SignalsExplained",
                    "08_KillPkillAndKillall", "09_NiceAndRenice", "10_BackgroundJobs", "11_NohupAndDisown",
                    "12_ZombiesAndOrphans", "13_DaemonsAndPid1"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_22_systemd", "Linux - usługi i systemd", List.of(
                    "01_WhatIsSystemd", "02_DaemonsAndServices", "03_UnitTypes", "04_UnitFilesStructure",
                    "05_SystemctlStartStopStatus", "06_EnableAndAutostart", "07_OverridesAndDaemonReload",
                    "08_WritingOwnServiceUnit", "09_RestartPoliciesAndDependencies",
                    "10_TargetsAndSystemStates", "11_SystemdTimers"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_23_start_systemu", "Linux - proces uruchamiania systemu", List.of(
                    "01_BootSequenceOverview", "02_BiosAndUefi", "03_GrubBootloader", "04_KernelAndInitramfs",
                    "05_InitSystemAndDefaultTarget", "06_RescueAndEmergencyMode", "07_ShutdownRebootAndPower"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_24_dyski_i_montowanie", "Linux - dyski, partycje i montowanie", List.of(
                    "01_BlockDevicesAndNaming", "02_LsblkBlkidAndUuid", "03_PartitionTablesMbrAndGpt",
                    "04_PartitioningFdiskAndParted", "05_CreatingFileSystemsMkfs", "06_MountingAndUnmounting",
                    "07_Fstab", "08_SwapSpace", "09_FsckCheckingAndRepair",
                    "10_GrowingPartitionsAndFileSystems"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_25_systemy_plikow_lvm_luks", "Linux - systemy plików, szyfrowanie, LVM i RAID", List.of(
                    "01_WhatIsAFileSystem", "02_NtfsFatAndExt4", "03_Ext4XfsAndBtrfs",
                    "04_InodesAndJournaling", "05_PartitionEncryptionBitlockerAndLuks", "06_LvmConcepts",
                    "07_LvmInPractice", "08_SoftwareRaidMdadm", "09_NetworkFileSystemsNfsAndSmb"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_26_logi", "Linux - logi systemowe", List.of(
                    "01_WhereLogsLive", "02_JournalctlBasics", "03_JournalctlFiltering", "04_RsyslogAndSyslog",
                    "05_Logrotate", "06_DmesgKernelMessages", "07_JavaApplicationLogsOnServer"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_27_harmonogram_zadan", "Linux - harmonogram zadań", List.of(
                    "01_CronBasics", "02_CrontabSyntax", "03_SystemCronDirectories",
                    "04_CronEnvironmentPitfalls", "05_AtOneTimeJobs", "06_SystemdTimersVsCron",
                    "07_ScheduledBackupsInPractice"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_28_monitorowanie_wydajnosci", "Linux - monitorowanie i wydajność", List.of(
                    "01_UptimeAndLoadAverage", "02_MemoryFreeAndCaches", "03_VmstatAndIostat",
                    "04_PerProcessResourceUsage", "05_LsofOpenFiles", "06_StraceSystemCalls", "07_OomKiller",
                    "08_UlimitAndResourceLimits", "09_JavaProcessDiagnostics", "10_SysstatHistoricalData"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_29_jadro_i_sprzet", "Linux - jądro i sprzęt", List.of(
                    "01_KernelVersionAndUname", "02_KernelModules", "03_SysctlParameters",
                    "04_ProcAndSysInterfaces", "05_HardwareInformation", "06_UdevAndDevices"
            ), CourseTrack.LINUX),

            // --- CZESC III. Siec i serwery ---
            new ChapterSeed("_lx_30_podstawy_sieci", "Linux - podstawy sieci TCP/IP", List.of(
                    "01_NetworkLayersModel", "02_IpAddressesAndClasses", "03_SubnetsAndCidr",
                    "04_PrivateAddressesAndNat", "05_GatewayAndRouting", "06_DnsExplained",
                    "07_PortsTcpAndUdp", "08_TcpHandshakeAndConnectionStates", "09_Ipv6Basics",
                    "10_VmNetworkingNatAndBridged"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_31_konfiguracja_sieci", "Linux - konfiguracja sieci", List.of(
                    "01_IpCommand", "02_IfconfigAndNetTools", "03_HostnameAndHostsFile",
                    "04_DnsResolverConfiguration", "05_NetplanOnUbuntu", "06_NetworkManagerNmcli",
                    "07_StaticIpConfiguration", "08_PingAndTraceroute", "09_DnsToolsDigAndNslookup"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_32_diagnostyka_sieci", "Linux - porty i diagnostyka sieci", List.of(
                    "01_NetstatBasics", "02_ListeningPortsNetstatPlnt", "03_NetstatWithPipesAndRoot",
                    "04_SsModernReplacement", "05_LsofForNetworkConnections", "06_NmapBasicScans",
                    "07_TcpScanTypes", "08_ServiceAndOsDetection", "09_NmapScriptsAndLocalNetwork",
                    "10_ScanningLegalityAndZenmap", "11_NetcatTesting", "12_TcpdumpBasics"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_33_ssh", "Linux - SSH", List.of(
                    "01_SshClientServerArchitecture", "02_InstallingSshAndConnecting",
                    "03_PublicKeyCryptography", "04_KeyGenerationSshKeygen", "05_PasswordlessLoginWithKeys",
                    "06_SshAgent", "07_SshConfigAliases", "08_SshdConfigAndPort", "09_SshHardening",
                    "10_SshTunnelsAndPortForwarding", "11_JumpHosts", "12_SshKeysForGitAndGithub"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_34_transfer_i_synchronizacja", "Linux - transfer i synchronizacja plików (scp, rsync)", List.of(
                    "01_ScpFileTransfer", "02_Sftp", "03_WhatIsRsync", "04_DeltaTransferAlgorithm",
                    "05_RsyncSyntaxAndArchiveFlag", "06_LocalSynchronization", "07_RsyncOverSsh",
                    "08_MirrorWithDelete", "09_ExcludeAndInclude", "10_PermissionsResumeAndBandwidth",
                    "11_RsyncBackups", "12_ServingFilesOverHttp"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_35_zapora_sieciowa", "Linux - zapora sieciowa", List.of(
                    "01_FirewallConcepts", "02_UfwOnUbuntu", "03_NftablesAndIptables", "04_Firewalld",
                    "05_FirewallForApplicationServer", "06_Fail2ban"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_36_serwery_www", "Linux - serwery WWW: Apache i Nginx", List.of(
                    "01_ApacheInstallationAndCheck", "02_ApacheStructureAndConfigFiles",
                    "03_ApacheVirtualHosts", "04_DirectoryIndexConfiguration", "05_PhpWithApache",
                    "06_NginxBasics", "07_ReverseProxyForSpringBoot", "08_HttpsWithLetsEncrypt",
                    "09_WebServerLogsAndTroubleshooting"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_37_bazy_danych_na_serwerze", "Linux - bazy danych na serwerze", List.of(
                    "01_MysqlInstallation", "02_MysqlDataDirectory", "03_MysqlBackups", "04_PhpMyAdmin",
                    "05_PostgresqlInstallation", "06_PostgresqlUsersAndAccess", "07_PostgresqlBackups",
                    "08_DatabaseServerSecurity"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_38_java_na_serwerze", "Linux - aplikacje Java na serwerze", List.of(
                    "01_JdkVsJreInstallation", "02_MultipleJdksUpdateAlternatives", "03_JavaHomeAndPath",
                    "04_MavenAndGradleInstallation", "05_RunningJarInBackground",
                    "06_SpringBootAsSystemdService", "07_JvmMemorySettingsOnServer",
                    "08_ApplicationConfigAndSecrets", "09_DeploymentAndRollback"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_39_bezpieczenstwo", "Linux - bezpieczeństwo i hartowanie systemu", List.of(
                    "01_SecurityPrinciples", "02_UpdatesAndPatching", "03_AccountAndPasswordSecurity",
                    "04_ListeningServicesAudit", "05_SelinuxBasics", "06_AppArmorBasics",
                    "07_AuthLogsAndLoginAudit", "08_FileIntegrityAndChecksums", "09_ServerSecurityChecklist"
            ), CourseTrack.LINUX),

            // --- CZESC IV. Automatyzacja, narzedzia i srodowiska ---
            new ChapterSeed("_lx_40_zmienne_srodowiskowe", "Linux - zmienne środowiskowe i konfiguracja powłoki", List.of(
                    "01_ShellVsEnvironmentVariables", "02_ExportAndEnv", "03_PathVariable",
                    "04_BashStartupFiles", "05_SystemWideEnvironment", "06_AliasesAndFunctionsInBashrc",
                    "07_PromptCustomizationPs1"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_41_skrypty_bash_podstawy", "Linux - skrypty bash: podstawy", List.of(
                    "01_FirstScriptAndShebang", "02_VariablesAndQuoting", "03_ReadingUserInput",
                    "04_IfConditions", "05_TestOperators", "06_CaseStatement", "07_ForLoops",
                    "08_WhileAndUntilLoops", "09_ArgumentsAndExitCodes", "10_FunctionsInBash"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_42_skrypty_bash_zaawansowane", "Linux - skrypty bash: poziom zaawansowany", List.of(
                    "01_ArraysAndAssociativeArrays", "02_StringManipulation", "03_ArithmeticInBash",
                    "04_StrictModeSetEuo", "05_TrapAndCleanup", "06_GetoptsOptionParsing",
                    "07_ProcessingFilesLineByLine", "08_DebuggingAndShellcheck", "09_PracticalAdminScripts",
                    "10_ScriptsForJavaApplications"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_43_powloki_i_terminale", "Linux - powłoki, terminale i tmux", List.of(
                    "01_BashVsZsh", "02_OhMyZshInstallation", "03_ZshrcAndPlugins", "04_Powerlevel10kTheme",
                    "05_TerminatorAndGuake", "06_TmuxBasics", "07_TmuxOnRemoteServers", "08_ScreenAlternative"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_44_wsl", "Linux - WSL (Windows Subsystem for Linux)", List.of(
                    "01_WhatIsWsl", "02_Wsl1VsWsl2", "03_InstallingWslAndDistributions",
                    "04_WindowsTerminalAndTools", "05_WslFilesAndWindowsDrives", "06_WslLimitations",
                    "07_GuiAppsAndXServer", "08_SystemdInWsl2", "09_WslVsVmVsServer"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_45_kontenery_i_docker", "Linux - kontenery od środka i Docker na WSL2", List.of(
                    "01_NamespacesAndCgroups", "02_DockerVsVirtualMachine",
                    "03_DockerDesktopAndWsl2Architecture", "04_PortsAndContainerCommunication",
                    "05_VolumesAndFileSystemPerformance", "06_DockerComposeInWsl2",
                    "07_DockerEngineOnNativeLinux", "08_DebuggingLimitationsAndWhenToUse"
            ), CourseTrack.LINUX),

            new ChapterSeed("_lx_46_projekt_koncowy", "Linux - projekt końcowy: serwer dla aplikacji Spring Boot", List.of(
                    "01_ProjectOverview", "02_ProvisioningServerAndUsers", "03_SecuringSshAndFirewall",
                    "04_InstallingJavaAndPostgresql", "05_DeployingApplicationAsService",
                    "06_NginxReverseProxyAndTls", "07_LogsMonitoringAndBackups", "08_AutomationScript",
                    "09_FinalChecklist"
            ), CourseTrack.LINUX)
    );

    private ChapterSeedData() {
    }
}
