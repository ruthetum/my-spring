# Kotlin MVC

## Structure
```
└── com
    └── example
        └── demo
            ├── account
            │   ├── application
            │   │   ├── impl
            │   │   │   └── AccountServiceImpl.kt
            │   │   └── AccountService.kt
            │   ├── domain
            │   │   ├── Account.kt
            │   │   └── AccountRepository.kt
            │   └── interfaces
            │       ├── controller
            │       │   ├── AccountController.kt
            │       └── dto
            │           ├── AccountRequsest.kt
            │           └── AccountResponse.kt
            ├── auth
            │   ├── application
            │   │   ├── impl
            │   │   │   └── AuthServiceImpl.kt
            │   │   └── AuthService.kt
            │   ├── domain
            │   │   ├── OAuth.kt
            │   │   ├── OAuthRepository.kt
            │   │   ├── LoginHistory.kt
            │   │   └── LoginHistoryRepository.kt
            │   └── interfaces
            │       ├── controller
            │       │   ├── AuthController.kt
            │       └── dto
            │           ├── AuthRequsest.kt
            │           └── AuthResponse.kt
            └── event
                ├── application
                │   ├── impl
                │   │   └── EventServiceImpl.kt
                │   └── EventService.kt
                ├── domain
                │   ├── Event.kt
                │   └── EventRepository.kt
                └── interfaces
                    ├── controller
                    │   ├── EventController.kt
                    └── dto
                        ├── EventRequsest.kt
                        └── EventResponse.kt
```