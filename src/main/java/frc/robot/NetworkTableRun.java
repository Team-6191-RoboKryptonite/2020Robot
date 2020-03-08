package frc.robot;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkTableRun {

//    public static void main(String[] args) {
//       new NetworkTableRun().run();
//    }


public double getTableNum(String key) {

  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("CVResultsTable");

  NetworkTableEntry Entry = table.getEntry(key);

  inst.startClientTeam(6191);  // where TEAM=190, 294, etc, or use inst.startClient("hostname") or similar
  inst.startDSClient();  // recommended if running on DS computer; this gets the robot IP from the DS

  // while (true) {
  //   try {
  //     Thread.sleep(1000);
  //   } catch (InterruptedException ex) {
  //     System.out.println("interrupted");
  //     return;
  //   }

    double value = Entry.getDouble(0.0);
    return value;



    //}
 }

   public void run() {

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("CVResultsTable");
    NetworkTableEntry xEntry = table.getEntry("centerX");
    //NetworkTableEntry yEntry = table.getEntry("y");

    inst.startClientTeam(6191);  // where TEAM=190, 294, etc, or use inst.startClient("hostname") or similar
    inst.startDSClient();  // recommended if running on DS computer; this gets the robot IP from the DS

    while (true) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        System.out.println("interrupted");
        return;
      }

      double x = xEntry.getDouble(0.0);
      //double y = yEntry.getDouble(0.0);
      System.out.println("X: " + x );

      }
   }
}