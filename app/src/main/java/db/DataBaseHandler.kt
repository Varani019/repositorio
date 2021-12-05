package db

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteDatabase
 import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import model.Person

class DataBaseHandler (ctx: Context): SQLiteOpenHelper(ctx,DB_NAME,null, DB_VERSION){

      companion object{
          private val DB_NAME= "Caduware"
          private val DB_VERSION = 1
          private val TABLE_NAME= "Person"
          private val ID = "id"
          private  val NAME = "Neme"
          private  val GENDER = "Gender"
          private val BIRTH = "Birth"
      }
    //poulando person
    @SuppressLint("Range")
    fun populatePerson(cursor: Cursor): Person{
        val person = Person()
        person.id = cursor.getInt(cursor.getColumnIndex(ID))
        person.nome = cursor.getString(cursor.getColumnIndex(NAME))
        person.td = cursor.getString(cursor.getColumnIndex(BIRTH))
        person.genero = cursor.getString(cursor.getColumnIndex(GENDER))
        return person

    }

      override fun onCreate(p0: SQLiteDatabase?) {
          //tabela sql
          val CREATE_TABLE = "CREATE TABLE $TABLE_NAME(" +
                  "$ID INTEGER PRIMARY KEY," +
                  "$NAME TEXT," +
                  "$GENDER TEXT ," +
                  "$BIRTH TEXT);"

          p0?.execSQL(CREATE_TABLE) //execute a tabela com o sql

      }

      override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
          val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
          p0?.execSQL(DROP_TABLE)
          onCreate(p0)

      }
      fun addPreson(person:Person){
          val p0 = writableDatabase
          val values = contentValuesOf().apply{
              put(NAME, person.nome)
              put(GENDER,person.genero)
              put(BIRTH, person.td)

          }
          p0.insert(TABLE_NAME,null,values)

      }
    //lendo pessoa
    fun getPerson(id:Int): Person {
        val  p0 = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $id"
        val cursor = p0.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        val person = populatePerson(cursor)
        cursor.close()
        return person
    }
    //lendo lista de pessoas
    fun getPersonList():ArrayList<Person> {
        val personList = ArrayList<Person>()
        val p0 = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME ORDER BY $NAME"  //ordenar por ordem alfabetica
        val cursor = p0.rawQuery(selectQuery, null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    val person = populatePerson(cursor)
                    personList.add(person)
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        return personList

    }
    //Atualizando pessoa
    fun updatePreson(person: Person){
        val p0 = writableDatabase
        val values = contentValuesOf().apply{
            put(NAME,person.nome)
            put(GENDER,person.genero)
            put(BIRTH, person.td)

        }
        p0.update(TABLE_NAME , values , "$ID=?", arrayOf(person.id.toString()))
    }
    // deletando pessoa
    fun deletPerson(id: Int){
        val p0 = writableDatabase
        p0.delete(TABLE_NAME, "$ID=?", arrayOf(id.toString()))
    }
    // procura pessoa 
     fun pesquisa (str : String) : ArrayList<Person>{
        val personList = ArrayList<Person>()
        val p0 = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $NAME LIKE '%$str%' OR $GENDER LIKE '%$str%' OR $BIRTH LIKE '%$str%'   ORDER BY $NAME"; //ordenar por ordem alfabetica
        val cursor = p0.rawQuery(selectQuery, null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    val person = populatePerson(cursor) 
                    personList.add(person)
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        return personList

     }

    }
