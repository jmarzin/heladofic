import java.io.File
import java.util.concurrent.TimeUnit
import javax.swing.{JOptionPane, JPasswordField, JTextField}

import org.openqa.selenium.{By, TimeoutException, WebDriverException, WebElement}
import org.openqa.selenium.firefox.FirefoxDriver

import scala.swing.TextField

/**
  * Created by jacquesmarzin on 15/02/2017.
  */
trait Utilitaires {
  implicit class Poste(p: TextField) {
    def verif : Boolean = {
      if(new File("restes_"+this.p.text+".db").exists) {
        BaseDeDonnees.ok
      } else false
    }
  }

  object Helios extends FirefoxDriver {

    manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)
    get("http://ulysse.dgfip")
    attends()
    Thread.sleep(500)
    var utilisateur = ""
    var password = ""
    if (!HeladoficApp.developpement) {
      utilisateur = HeladoficApp.utilisateur.text
      password = HeladoficApp.motDePasse.password.toString
    } else {
      utilisateur = "jacques.marzin"
      password = ""
    }
    get("http://portailapplicatif.appli.impots")
    val handles = getWindowHandles.toArray
    switchTo.window(handles(handles.length - 1).toString)
    attends()
    val identifiant = findElements(By.id("identifiant"))
    if (!identifiant.isEmpty) {
      identifiant.get(0).sendKeys(utilisateur)
      val pwd = findElement(By.id("secret_tmp"))
      pwd.sendKeys(password)
      findElement(By.className("valid")).clickw()
    }
    findElement(By.className("nom_appli"))
    if (!getPageSource.contains("http://helios.appli.impots")) {
      throw new WebDriverException()
    }
    get("http://helios.appli.impots")

    def attends(): Unit = {
      for(_ <- 1 to 100){
        if(executeScript("return document.readyState") == "complete")return
        Thread.sleep(100)
      }
      throw new TimeoutException("trop d'attente", new Throwable)
    }

    implicit class clickEtAttend(webelement: WebElement) {
      def clickw() : Unit = {
        webelement.click()
        attends()
      }
    }

    def adonis(vect: Vector[Any]) : Vector[Any] = {
      vect
    }

    def ficoba(vect: Vector[Any]) : Vector[Any] = {
      vect
    }
  }


  def litDonnees(spi: String): Vector[Any] = {
    var vect = Vector[Any]()
    vect = Helios.adonis(vect)
    vect = Helios.ficoba(vect)
    vect
  }
}
