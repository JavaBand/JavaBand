package simplechat;






public interface MusicIF 
{
  /**
   * Method that when overriden is used to display objects onto
   * a UI.
   */
  public abstract void sing(String notePlayed);
  
  public String buildNote(String noteNumber); 
  
}
