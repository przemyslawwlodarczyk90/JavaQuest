package com.example.javaquest._00_tableOfContents;

import java.util.List;

/**
 * Spis treści całego kursu Java Quest — jedna klasa wypisująca strukturę
 * wszystkich rozdziałów i lekcji. Uruchom main(), żeby zobaczyć pełną mapę
 * kursu w konsoli.
 *
 * UWAGA: ta klasa jest czystą DOKUMENTACJĄ/NAWIGACJĄ, nie lekcją kursu —
 * przy dodawaniu nowego rozdziału lub lekcji pamiętaj o dopisaniu go tutaj.
 */
public class _TableOfContents {

    record Chapter(String pakiet, String tytul, List<String> lekcje) {
    }

    static final List<Chapter> ROZDZIALY = List.of(

            new Chapter("_01_fundamentals", "Podstawy Javy", List.of(
                    "00_JavaPlatformBasics", "01_Variables", "02_Operators", "03_Conditionals",
                    "04_Loops", "05_Arrays", "06_StringsAndBuilder", "07_DateAndTime",
                    "08_MathOperations", "09_BigNumberTypes", "10_HeapAndStack", "11_TypeCasting",
                    "12_BinaryAndHex", "13_BitwiseOperators", "14_GarbageCollector",
                    "15_RandomAndSecureRandom", "16_Exceptions"
            )),

            new Chapter("_02_oop", "Programowanie obiektowe (OOP)", List.of(
                    "01_ClassesAndObjects", "02_Encapsulation", "03_Constructors", "04_StaticKeyword",
                    "05_Inheritance", "06_Polymorphism", "07_AbstractClasses", "08_Interfaces",
                    "09_AccessModifiers", "10_FinalKeyword", "11_ObjectClass", "12_InnerClasses",
                    "13_Enums", "14_Records", "15_DesignPatterns"
            )),

            new Chapter("_03_collections", "Java - Kolekcje", List.of(
                    "01_ArrayList", "02_Iteration", "03_CollectionMethods", "04_LinkedList",
                    "05_HashSet", "06_TreeSet", "07_Comparator", "08_HashMap", "09_PriorityQueue",
                    "10_StreamsIntro", "11_StreamsCollectors", "12_StreamsTerminal", "13_StreamsAdvanced",
                    "14_Optional", "15_LinkedHashSet", "16_LinkedHashMap", "17_TreeMap", "18_Deque",
                    "19_ComparableVsComparator", "20_ConcurrentCollections", "21_LegacyCollections",
                    "22_Queue", "23_SpecialMaps"
            )),

            new Chapter("_04_io", "Input/Output i praca z plikami", List.of(
                    "01_IOIntroduction", "02_FileReaderWriter", "03_BufferedReaderWriter",
                    "04_BufferedStreams", "05_DataStreams", "06_PrintWriterAndStream", "07_Scanner",
                    "08_FileClass", "09_PathAndPaths", "10_FilesClass", "11_RandomAccessFile",
                    "12_Charset", "13_TryWithResources", "14_FileExceptions", "15_NioChannelsAndBuffers",
                    "16_ObjectSerialization", "17_SerialVersionUID", "18_TransientKeyword",
                    "19_JsonIntro", "20_Gson", "21_Jackson", "22_SerializableVsJson", "23_CSV", "24_ZIP"
            )),

            new Chapter("_05_multithreading", "Multithreading / Wątki w Javie", List.of(
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

            new Chapter("_06_networking", "Programowanie sieciowe", List.of(
                    "01_NetworkingIntroduction", "02_InetAddress", "03_Socket", "04_SocketWhois",
                    "05_SocketHttpDownload", "06_ServerSocket", "07_ServerSocketMultithreaded",
                    "08_URL", "09_URLConnection", "10_HttpURLConnection", "11_HttpProtocol",
                    "12_JsonOverNetwork", "13_XmlParsing", "14_HtmlUnit"
            )),

            new Chapter("_07_servlets", "Servlety – Java Web bez Spring Boot", List.of(
                    "01_ServletApiIntroduction", "02_ServletContainers", "03_ServletProjectSetup",
                    "04_HttpServlet", "05_HttpServletRequest", "06_HttpServletResponse", "07_GetAndPost",
                    "08_OtherHttpMethods", "09_FormParameters", "10_Cookies", "11_HttpSession",
                    "12_ServletConfig", "13_ServletContext", "14_Filters", "15_Listeners",
                    "16_ServletAnnotations", "17_ForwardAndRedirect", "18_FileUpload", "19_JSP"
            )),

            new Chapter("_08_sql", "SQL i relacyjne bazy danych", List.of(
                    "01_DatabaseIntroduction", "02_RelationalModel", "03_TableDesign", "04_SqlDataTypes",
                    "05_NullValues", "06_DataConstraints", "07_Normalization", "08_DDL", "09_DML",
                    "10_Select", "11_Filtering", "12_Sorting", "13_Grouping", "14_Joins",
                    "15_SqlRelationships", "16_Subqueries", "17_Views", "18_Indexes", "19_Transactions",
                    "20_TransactionIsolationLevels"
            )),

            new Chapter("_09_jdbc", "JDBC – Java Database Connectivity", List.of(
                    "01_JdbcIntroduction", "02_JdbcDriver", "03_Connection", "04_Statement",
                    "05_PreparedStatement", "06_ResultSet", "07_JdbcInsert", "08_JdbcSelect",
                    "09_JdbcUpdate", "10_JdbcDelete", "11_CreateAndDropTableFromJava",
                    "12_TryWithResourcesInJdbc", "13_JdbcExceptions", "14_SqlInjection",
                    "15_JdbcTransactions", "16_BatchProcessing", "17_ResultSetMapping", "18_DomainModel",
                    "19_Dto", "20_Mapper"
            )),

            new Chapter("_10_dao", "DAO i architektura dostępu do danych", List.of(
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

            new Chapter("_11_buildtools", "Build Tools w Javie – Ant, Maven, Gradle", List.of(
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

            new Chapter("_12_hibernate", "Hibernate – ORM i JPA w praktyce", List.of(
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

            new Chapter("_13_libraries", "Biblioteki – popularne narzędzia w ekosystemie Javy", List.of(
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

            new Chapter("_14_advancedjava", "Zaawansowane mechanizmy języka Java", List.of(
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

            new Chapter("_15_jvm_internals", "JVM, pamięć i wydajność", List.of(
                    "01_JdkJreJvmAndSpecification", "02_CompilationAndBytecode", "03_ClassLoadingMechanics",
                    "04_CustomClassLoaders", "05_ClasspathVsModulepath", "06_HeapStackMetaspace",
                    "07_ReferenceTypesAndStringPool", "08_GarbageCollectionFoundations",
                    "09_GarbageCollectorAlgorithms", "10_G1GcDeepDive", "11_LowLatencyCollectors",
                    "12_GcTuningAndLogging", "13_JitCompilerBasics", "14_EscapeAnalysisAndInlining",
                    "15_MemoryLeaksInJava", "16_HeapDumpBasics", "17_ThreadDumpBasics",
                    "18_JavaFlightRecorderBasics", "19_ProfilingBasics",
                    "20_JvmTuningAndBestPracticesCapstone"
            )),

            new Chapter("_16_clean_code", "Clean Code i refactoring", List.of(
                    "01_WhatIsCleanCode", "02_Naming", "03_Comments", "04_MethodsAndFunctions",
                    "05_Formatting", "06_ClassesAndResponsibilities", "07_SingleResponsibilityPrinciple",
                    "08_OpenClosedPrinciple", "09_LiskovSubstitutionPrinciple",
                    "10_InterfaceSegregationPrinciple", "11_DependencyInversionPrinciple",
                    "12_CouplingCohesionAndLawOfDemeter", "13_DryKissYagni", "14_CodeSmells",
                    "15_RefactoringBasics", "16_RefactoringCatalog", "17_ExceptionDesign",
                    "18_NullHandling", "19_ImmutabilityInPractice", "20_StaticAnalysisTools",
                    "21_LegacyCodeAndTechnicalDebt", "22_CodeReviewBestPracticesAndCapstone"
            )),

            new Chapter("_17_architecture", "Architektura aplikacji Java", List.of(
                    "01_WhyArchitectureMatters", "02_ArchitectureDecisionRecords", "03_LayeredArchitecture",
                    "04_ControllerServiceRepository", "05_DomainModelVsAnemicModel",
                    "06_BoundedContextsAndDddLite", "07_DtoEntityMapper", "08_ApiVersioningAndCompatibility",
                    "09_PackageByLayerVsPackageByFeature", "10_DependencyDirection",
                    "11_HexagonalArchitectureIntro", "12_PortsAndAdapters", "13_TransactionBoundaries",
                    "14_CachingArchitecture", "15_ValidationArchitecture", "16_ErrorHandlingArchitecture",
                    "17_ModularMonolith", "18_EventDrivenCommunicationBetweenModules",
                    "19_WhenMicroservicesMakeSense", "20_ArchitectureCapstone"
            )),

            new Chapter("_18_rest_api", "REST API i projektowanie HTTP", List.of(
                    "01_HttpDeepDive", "02_RestIntroduction", "03_ResourcesAndEndpoints",
                    "04_HttpMethods", "05_StatusCodes", "06_RequestResponseBody",
                    "07_ContentNegotiation", "08_JsonApiDesign", "09_PathVariablesAndQueryParams",
                    "10_PaginationSortingFiltering", "11_HttpCachingAndConditionalRequests",
                    "12_ErrorResponseDesign", "13_ValidationErrors", "14_Versioning",
                    "15_Idempotency", "16_RateLimitingAndThrottling", "17_PostmanBasics",
                    "18_OpenApiSwaggerIntro", "19_RestVsRpcVsGraphQL",
                    "20_RestApiBestPracticesAndCapstone"
            )),

            new Chapter("_19_security_basics", "Podstawy bezpieczeństwa aplikacji", List.of(
                    "01_AuthenticationVsAuthorization", "02_PasswordHashing", "03_BCrypt",
                    "04_SessionsAndCookies", "05_JwtIntroduction", "06_OAuth2AndOpenIdConnectIntro",
                    "07_AuthorizationPatternsAndRbac", "08_HttpsTlsBasics", "09_Cors", "10_Csrf",
                    "11_Xss", "12_SecurityHeaders", "13_SqlInjectionDeepDive",
                    "14_InsecureDeserialization", "15_XxeAndXmlExternalEntityAttacks",
                    "16_PathTraversalAndFileUploadSecurity", "17_InputValidation",
                    "18_SecretsManagement", "19_SecureLoggingAndAuditing",
                    "20_DependencyAndSupplyChainSecurity", "21_OwaspTop10OverviewAndCapstone"
            ))
    );

    public static void main(String[] args) {

        int lacznaLiczbaLekcji = ROZDZIALY.stream().mapToInt(r -> r.lekcje().size()).sum();

        System.out.println("=".repeat(70));
        System.out.println("JAVA QUEST — SPIS TREŚCI KURSU");
        System.out.println("=".repeat(70));
        System.out.println("Rozdziałów: " + ROZDZIALY.size() + "   Lekcji łącznie: " + lacznaLiczbaLekcji);
        System.out.println();

        for (Chapter rozdzial : ROZDZIALY) {
            System.out.println("-".repeat(70));
            System.out.println(rozdzial.pakiet() + "  —  " + rozdzial.tytul()
                    + "  (" + rozdzial.lekcje().size() + " lekcji)");
            System.out.println("-".repeat(70));
            for (String lekcja : rozdzial.lekcje()) {
                System.out.println("  Lesson" + lekcja);
            }
            System.out.println();
        }

        System.out.println("=".repeat(70));
        System.out.println("Koniec spisu treści.");
        System.out.println("=".repeat(70));
    }
}
