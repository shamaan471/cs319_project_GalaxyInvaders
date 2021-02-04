package Controller;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.Timer;
import Model.*;
import View.*;

public class SessionListener implements ActionListener, KeyListener {

    ModelManager modelManager = ModelManager.getInstance();
    private Player player;
    private GameSceneView gameSceneView;



    //variables used to spawn objects and repaint
    private Timer timer;
    private int time = 0; //cumulated time

    private int timeShooterSpawn = 0;  //time since last shooter spawn

    private int timeKamikazeSpawn = 0; //time since the last kamikaze spawn

    private int timeSplitterSpawn = 0; //time since the last splitter spawn

    private int collisionDetectionTime = 0; //time since the last collision check

    private int shieldCheckTime = 0;

    private int shieldTime = 0;

    private int gameOverDetectionTime = 0;

    private int enemyRemovalTime = 0;

    private int enemyFireTime = 0;

    private int missileTargetCheckTime = 0;

    private ArrayList<Integer> pressedKeys = new ArrayList<>();




    public SessionListener(){
        setVariables();
    }


    public void setVariables(){


        player = modelManager.getPlayer();

        gameSceneView = GameController.getGameFrame().getGameSceneView();
        gameSceneView.setPlayer(modelManager.getPlayer());

        timer = new Timer(30, this);


    }


    public void setTimer(boolean startTimer){
        //turn on/off timer
        if (startTimer == true) timer.start();
        else timer.stop();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        checkIfGameOver();

        setPositionOfObjects();

        checkSpawnEnemy();

        checkForHumansAndBonuses();


        checkCollisions(); //checking for collisions

        checkEnemyRemoval();  //checks whether to remove enemy

        checkEnemyFire();

        checkMissileTarget();

        checkPlayerShield();

        gameSceneView.repaint();

        updateClocks();

    }


    //checks for the shield status of player and removes it after some time
    public void checkPlayerShield(){
        if(shieldCheckTime > 10){
            shieldCheckTime = 0;
            if (player.getIsShielded() && shieldTime < player.getMAX_SHIELD_TIME()){
                shieldTime++;
            }
            else if(player.getIsShielded() && shieldTime >= player.getMAX_SHIELD_TIME()){
                shieldTime = 0;
                player.setIsShielded(false);
            }
        }

    }


    //checks if any humans have been spawned
    public void checkForHumansAndBonuses(){
        gameSceneView.updateListOfHumans(modelManager.getListOfHumans());
        gameSceneView.updateListOfBonuses(modelManager.getListOfBonuses());
    }




    //updates the target for every missile on scene
    public void checkMissileTarget(){
        if(missileTargetCheckTime > 500){
            modelManager.setClosestEnemyToMissile();
        }
    }


    //checks if the player health is 0 and stops the game
    public void checkIfGameOver(){

        if (gameOverDetectionTime > 10) {

            gameOverDetectionTime = 0;

            if (player.getHealth() <= 0) {
                setTimer(false);
                gameSceneView.popGameOver();
            }

            /*
            else if (modelManager.getBoss().getHealth() <=0){
                setTimer(false);
                gameSceneView.popGameWon();
            }
            */
        }
    }


    //updates the position of every model after the tick
    public void setPositionOfObjects(){

        //sets new position of player, depending on pressed keys
        for (int pressedKey : pressedKeys)
        {
            player.calculateMovement(pressedKey);
        }

        //updating enemy position
        modelManager.updatePositionOfEnemies();

        //updating player bullet position
        modelManager.setNewPositionOfBullets();

        //updating enemy bullet position
        modelManager.updatePositionOfEnemyBullets();

        //updating human position
        modelManager.updateHumanMovement();
    }





    //check if can spawn new enemy ship
    public void checkSpawnEnemy()
    {
        ///////////////////////////////////////////////////////////////////////////////
        modelManager.setBoss();
        gameSceneView.setBoss(modelManager.getBoss());

        if(!modelManager.getIsBossBattle()) {

            //spawns new enemy ship and resets time of spawn,
            if (timeShooterSpawn > 2000) {
                modelManager.setEnemyShooter();
                gameSceneView.updateListOfEnemyShooters(modelManager.getEnemyShooters());
                timeShooterSpawn = 0;
            }

            //spawns new enemy kamikaze and resets time of spawn
            if (timeKamikazeSpawn > 3000) {
                modelManager.setEnemyKamikaze();
                gameSceneView.updateListOfEnemyKamikazes(modelManager.getEnemyKamikazes());
                timeKamikazeSpawn = 0;
            }

            //spawns new enemy splitter and resets time of spawn
            if (timeSplitterSpawn > 5000) {
                modelManager.setEnemySplitter();
                gameSceneView.updateListOfEnemySplitters(modelManager.getEnemySplitters());
                timeSplitterSpawn = 0;
            }
        }

    }


    //checks the collisions
    public void checkCollisions(){
        if(collisionDetectionTime > 100){   //eliminates the need to check collision after every tick
            collisionDetectionTime = 0;
            modelManager.checkIfEnemyInCollision(modelManager.getPlayer());
            modelManager.checkIfPlayerBulletsInCollision();
            modelManager.checkIfEnemyBulletsInCollision();
            modelManager.checkIfHumansAndBonusInCollision();
        }
    }


    public void checkEnemyRemoval(){
        modelManager.checkIfEnemyDestroyed();
        //gameSceneView.updateListOfBonuses(modelManager.getListOfBonuses());
        updateGameLabels();
    }


    public void checkEnemyFire(){

        if(enemyFireTime >= 600){
            enemyFireTime = 0;
            modelManager.setEnemyBlasterBullets();
            gameSceneView.updateListOfEnemyShooterBlasters(modelManager.getListOfEnemyShooterBullets());
        }
    }


    public void updateClocks(){
        timeShooterSpawn += timer.getDelay();
        timeKamikazeSpawn += timer.getDelay();
        timeSplitterSpawn += timer.getDelay();
        collisionDetectionTime += timer.getDelay();
        enemyRemovalTime += timer.getDelay();
        enemyFireTime += timer.getDelay();
        gameOverDetectionTime += timer.getDelay();
        missileTargetCheckTime += timer.getDelay();
        shieldCheckTime += timer.getDelay();
    }



    //sets type of weapon depending on clicked button with weapons
    public void setTypeOfWeaponForPlayer(String typeOfWeapon)
    {
        //GameController.getGameModel().getPlayer().setTypeOfWeaponEquiped(typeOfWeapon);
        modelManager.getPlayer().setTypeOfWeaponEquiped(typeOfWeapon);
    }

    public void updateGameLabels()
    {
        gameSceneView.updateScore(modelManager.getPlayer().getScore());
        gameSceneView.updateHealth(modelManager.getPlayer().getHealth());
        gameSceneView.updateHuman(modelManager.getPlayer().getHumanAboard());
        gameSceneView.updateLaser(modelManager.getPlayer().getRemainingLasers());
        gameSceneView.updateMissile(modelManager.getPlayer().getRemainingMissiles());
    }



    @Override
    public void keyTyped(KeyEvent e) {



    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(!pressedKeys.contains(code))
        {
            switch(code) {

                //spacebar pressed
                case 32:
                    if (modelManager.getPlayer().getTypeOfWeaponEquiped().equals("blaster")) {
                        //SPACE pressed, player shoots bullets (missile, blaster, laser, bomb)
                        modelManager.setPlayerBlasterBullets();
                        gameSceneView.updateListOfPlayerBulletBlasters(modelManager.getListOfPlayerBulletBlasters());
                    } else if (modelManager.getPlayer().getTypeOfWeaponEquiped().equals("missile")) {
                        modelManager.setPlayerMissile();
                        gameSceneView.updateListOfPlayerMissiles(modelManager.getListOfPlayerMissiles());
                    } else if (modelManager.getPlayer().getTypeOfWeaponEquiped().equals("laser")) {
                        modelManager.setPlayerLaser();
                        gameSceneView.updateListOfPlayerLasers(modelManager.getListOfPlayerLasers());
                    }
                    break;
                case 37:
                    //LEFT arrow pressed, move left
                    pressedKeys.add(code);
                    break;
                case 38:
                    //UP arrow pressed, move up
                    pressedKeys.add(code);
                    break;
                case 39:
                    //RIGHT arrow pressed, move right
                    pressedKeys.add(code);
                    break;
                case 40:
                    //DOWN arrow pressed, move down
                    pressedKeys.add(code);
                    break;

                case 27:
                    GameController.getGameFrame().pauseGameSession();
                    GameController.sessionListener.setTimer(false);
                    break;

                case 81:
                    //Q pressed, weapon changed for missiles
                    setTypeOfWeaponForPlayer("missile");
                    break;

                case 87:
                    //W pressed, weapon changed for blaster
                    setTypeOfWeaponForPlayer("blaster");
                    break;

                case 69:
                    //W pressed, weapon changed for blaster
                    setTypeOfWeaponForPlayer("laser");
                    break;

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        for(int i = 0; i < pressedKeys.size(); i++)
        {
            if (pressedKeys.get(i)  == code)
                pressedKeys.remove(i);
        }
    }
}
