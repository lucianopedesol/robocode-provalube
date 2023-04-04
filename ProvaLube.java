package sample;
import robocode.*;
import robocode.util.*;

public class ProvaLube  extends AdvancedRobot {
     private int moveDirection = 1;

    public void run() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        
        while (true) {
            setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
            setAhead(100);
            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        double radarTurn = getHeadingRadians() + e.getBearingRadians() - getRadarHeadingRadians();
        double gunTurn = getHeadingRadians() + e.getBearingRadians() - getGunHeadingRadians();
        setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
        setTurnGunRightRadians(Utils.normalRelativeAngle(gunTurn)); 
	double enemyBearing = e.getBearingRadians();
	double enemyHeading = e.getHeadingRadians();
	double bearingFromGun = Utils.normalRelativeAngle(enemyBearing - getGunHeadingRadians() + enemyHeading);
	    
    	if (Math.abs(bearingFromGun) < Math.toRadians(3)) {
	    setFire(3);
    	} 
        fire(1);
    }
	
    public void onHitByBullet(HitByBulletEvent e) {
        // inverte a direção do movimento
        moveDirection = -moveDirection; 

        // Vira para um lado aleatório
        if (Math.random() > 0.5) {
            setTurnRight(90);
        } else {
            setTurnLeft(90);
        }

        // Move para trás
        setBack(100);
        execute();
    }
	
 
}
