import java.sql.{Connection, DriverManager, Statement}

/**
  * Created by jacquesmarzin on 16/02/2017.
  */
object BaseDeDonnees {
  var connexion: Connection = _
  var ordreSql: Statement = _
  connexion = DriverManager.getConnection("jdbc:sqlite:restes_"+HeladoficApp.poste.text+".db")
  ordreSql = connexion.createStatement

  def ok : Boolean = {
    try {
      val chaine = "select * from debiteur where nfp <> '' limit 1;"
      val rs = ordreSql.executeQuery(chaine)
      if(rs.next()) true else false
    } catch {
      case e: Exception =>
        false
    }
  }

  def litDeb : Vector[String] = {
    val chaine = "select * from debiteur where nfp <> '';"
    val rs = ordreSql.executeQuery(chaine)
    var vect = Vector[String]()
    while(rs.next()) {
      vect = vect :+ rs.getString(1)
    }
    vect
  }
}
