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
	// Ativa o radar para mirar em quem for encontrando pelo caminho 
        double radarTurn = getHeadingRadians() + e.getBearingRadians() - getRadarHeadingRadians();
        double gunTurn = getHeadingRadians() + e.getBearingRadians() - getGunHeadingRadians();
        setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
        setTurnGunRightRadians(Utils.normalRelativeAngle(gunTurn)); 
	    
	//Ângulo em radianos do robô adversário em relação ao seu.
	double enemyBearing = e.getBearingRadians();
	//Retorna o ângulo em radiaons do adversário em relação a tela.
	double enemyHeading = e.getHeadingRadians();
	// Calcula o ângulo necessário para girar a arma e mirar no inimigo
	double bearingFromGun = Utils.normalRelativeAngle(enemyBearing - getGunHeadingRadians() + enemyHeading);
	// Se o angulo for menor que 3 atira com mais força 
    	if (Math.abs(bearingFromGun) < Math.toRadians(3)) {
	    setFire(3);
    	} 
	// Se não, fica atirando normalmente
        fire(1);
    }
	
    //Fugir para alguma direção aleatoria quando for atingido.
    public void onHitByBullet(HitByBulletEvent e) {
        // Muda a direção do movimento atual
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
