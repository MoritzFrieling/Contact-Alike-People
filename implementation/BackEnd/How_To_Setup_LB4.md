# LB4 App from Scratch
## Step 1
Use some LoopBack 4 commands to setup database source and models

- lb4 app
	- cap-back-end
- lb4 datasource
	- dataSourceName: MariaDB
	- connector: mysql
	- credentials...
- lb4 model
	- modelName: User
	- modelBaseClass: Entity
	- (repeat for other models)
- lb4 repository
	- datasource: MariaDB
	- model: User
	- repositoryBaseClass: DefaultCrudRepository
	- (repeat for other models)
- lb4 relation
	- hasMany
	- Source: User
	- Target: UserInterest
	- ForeignKey: userId

## Step 2
Add the foreign key constraint to the corresponding model definitions by changing their '@model' decorator to include a 'foreignKeys' object as followed:

```ts
// src/models/user-interest.model.ts

@model({  
  settings: {  
  strict: true,  
    foreignKeys: {  
	  // eslint-disable-next-line @typescript-eslint/naming-convention  
      fk_userInterest_userId: {  
        name: 'fk_userInterest_userId',  
        entity: 'User',  
        entityKey: 'id',  
        foreignKey: 'userId',  
      },  
    },  
  }  
})
```

## Step 3
Enable Auto Migration for Database on startup
In the main file of the app, use the migrateSchema command to setup the remote database tables automatically

```ts
// src/index.ts

await app.boot();

// add these lines
await app.migrateSchema({
    existingSchema: 'alter',
    models: [ 'User', 'UserInterest' ]
});

await app.start();
```

# Resources
- [Getting Started](https://loopback.io/doc/en/lb4/Getting-started.html)
- [Setting Up JWT](https://loopback.io/doc/en/lb4/Authentication-tutorial.html#before-we-begin)
