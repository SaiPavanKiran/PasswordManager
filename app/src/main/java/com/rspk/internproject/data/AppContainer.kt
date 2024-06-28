package com.rspk.internproject.data

import android.content.Context

interface AppContainer {
    val repositoryInstance:Repository
}



class AppContainerImpl(context: Context) : AppContainer{

    override val repositoryInstance: Repository by lazy {
        PasswordManagerRepository(
            ApplicationRoomDatabase
                .getRoomInstance(context)
                .passwordManagerDaoInstance()
        )
    }
}