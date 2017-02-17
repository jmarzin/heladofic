import java.awt.Color

import scala.swing.event.{EditDone, FocusLost}
import scala.swing.{BoxPanel, FlowPanel, Label, MainFrame, Orientation, PasswordField, ProgressBar, SimpleSwingApplication, Swing, TextField}

/**
  * Created by jacquesmarzin on 15/02/2017.
  */
object HeladoficApp extends SimpleSwingApplication with Utilitaires {

  val poste = new TextField{text = ""; columns = 15}
  val erreur = new Label{text = " ";foreground = Color.RED}
  val utilisateur = new TextField{text =""; columns = 30;editable=false}
  val motDePasse = new PasswordField{text ="";columns =30;editable=false}
  val jauge = new ProgressBar
  val lignePoste = new FlowPanel(new Label("Poste : "), poste)
  val ligneUtilisateur = new FlowPanel(new Label("Nom d'utilisateur : "),utilisateur)
  val ligneMotDePasse = new FlowPanel(new Label("Mot de passe : "),motDePasse)
  val developpement = true

  def top = new MainFrame {

    title = "Récupération données débiteur"
    contents = new BoxPanel(Orientation.Vertical) {
      contents += (lignePoste,ligneUtilisateur,ligneMotDePasse,jauge,erreur)
    }
    listenTo(poste,utilisateur,motDePasse)

    reactions += {
      case EditDone(`poste`) =>
        if(poste.verif) {
          poste.enabled = false
          utilisateur.editable = true
          motDePasse.editable = true
          erreur.text = " "
        } else {
          erreur.text = "Base de données non trouvée ou rien à traiter."
          poste.requestFocus()
        }
      case EditDone(`utilisateur`) =>
        if(utilisateur.text.trim.isEmpty) {
          erreur.text = "Champ utilisateur obligatoire"
          utilisateur.requestFocus()
        } else {
          erreur.text = " "
          motDePasse.requestFocus()
        }
      case EditDone(`motDePasse`) =>
        if(motDePasse.password.isEmpty) {
          erreur.text = "Champ mot de passe obligatoire"
          motDePasse.requestFocus()
        } else {
          erreur.text = " "
          val listeDeb = BaseDeDonnees.litDeb
          System.setProperty("webdriver.firefox.bin", "D:\\firefox31\\firefox.exe")
          for(deb<-listeDeb){
            val infos = litDonnees(deb)
          }

        }
    }
  }

}
