


# BlokSQLiteAPI
An SQLite API to manage sqlite databases.


## How to connect database
1. Create SQLHandler class of your plugin.
2. Extend mc.obliviate.bloksqliteapi.SQLHandler
3. Call super connect method to connect database. The paramater of this method is name of the database.
4. Override onConnect() method to use on connect event. We need it to call a create table method.
5. Create a createTable() method and create your table. (I explained below)

```
public class MySQLManagerClass extends SQLHandler {
	public MySQLManagerClass(BlokLogger plugin) {
		super(plugin);
		connect();
	}

	public void connect() {
		super.connect("database");
	}

	@Override
	public void onConnect() {
		Bukkit.getLogger().info("SQLite DB Connected successfully");
		createTable();
	}


	public SQLTable createTable() {
		//table stuff
	}

}


```


## How to use
### Create a table object
1. First param, specify your table name
2. Second param, specify name of field that you'll use instead of ID
3. Third param, add a field to your table.
You know. Fields has some properties, data type, not null, unique, primary key...
You can specify this properties too.
4. No no no! You don't need (goddamn) the long 'create table...' text. You given us what we need. Just call create() method. I coded that API to do not write that text. I hate it i hate.



```
public SQLTable createTable() {

  final SQLTable sqlTable = new SQLTable("school3", "no")


    //                       isNotNull, isUnique, isPrimaryKey
    .addField("no", DataType.INTEGER, true, true, true)

    //Other params is false as default
    .addField("name", DataType.TEXT)
    .addField("class", DataType.TEXT);

  return sqlTable.create();
}
```



### Data Usage
Let's test our database. The mission is "Put a student named 'Hamza', if the student already exist then change name of student with 'Adnan'."
1. Specify an id to make an example. i choose 306. (because that is my school number xd)
2. Check your table and ask 'is this id inserted before?'



```
public void test(SQLTable sqltable) {

  int id = 306;

  if (sqltable.exist(id)) {
    }
}
```



3. If the answer is yes, you must update. Your table already has another fields you need. So, you can specify fields only that you want to change.



```
public void test(SQLTable sqltable) {

  int id = 306;

  if (sqltable.exist(id)) {
    sqltable.update(sqltable.createUpdate(id).putData("name", "ali"));
  }
}
```



4. If the answer is no, you must insert. Your table does not have another fields you need. So you must specify all fields.



```
public void test(SQLTable sqltable) {

  int id = 306;

  if (sqltable.exist(id)) {
    sqltable.update(sqltable.createUpdate(id).putData("name", "Adnan"));
  } else {
    sqltable.insert(sqltable.createUpdate(id).putData("name", "Hamza").putData("class", "12E").putData("no", id));
  }
}
```

5. That is test so i must show you all things. Next mission is: Delete data you putted.

```
public void test(SQLTable sqltable) {

  int id = 306;

  if (sqltable.exist(id)) {
    sqltable.update(sqltable.createUpdate(id).putData("name", "Adnan"));
  } else {
    sqltable.insert(sqltable.createUpdate(id).putData("name", "Hamza").putData("class", "12E").putData("no", id));
  }

  sqltable.delete(306);
  
}
```

6. Perfect. Also there is shorter a way too. But that way only puts student named 'Hamza'. Does not changes names. But if you want to know is updated or inserted, the insertOrUpdate() method returns true if updated.

```
public void test(SQLTable sqltable) {

  int id = 306;

  sqltable.insertOrUpdate(sqltable.createUpdate(id).putData("name","Hamza").putData("class","12E").putData("no",id));

  sqltable.delete(306);
  
}
```

7. If you want to get result set

  
```
public void test(SQLTable sqltable) {

  int id = 306;

  ResultSet rs = sqltable.select(id);
  
}
```



