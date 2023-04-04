package sample;
import robocode.*;
import robocode.util.*;

public class ProvaLube  extends AdvancedRobot {
     private int moveDirection = 1;

    public void run() {
	//Define a arma para girar independente da rotação do robô.
        setAdjustGunForRobotTurn(true);
	//Define o radar para girar independente da curva da arma.
        setAdjustRadarForGunTurn(true);
        
        while (true) {
	    // Matem o radar girando infinitamente, a idéia é ficar buscando outros robos que estejam se movendo
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
	//Retorna o angulo em radianos em que o robô escaneado está virado
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
