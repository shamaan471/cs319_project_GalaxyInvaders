package Model;
import Controller.SoundController;
import Model.Bonus.*;
import Model.Enemy.*;
import Model.Bullet.*;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class ModelManager {

    static private Player player;
    static private ArrayList<EnemyShooter> enemyShooters;

    static private ArrayList<EnemyKamikaze> enemyKamikazes;

    static private ArrayList<EnemySplitter> enemySplitters;

    static private ArrayList<BulletBlaster> listOfPlayerBulletBlasters;

    static private ArrayList<BulletBlaster> listOfEnemyShooterBullets;

    static private ArrayList<BulletMissile> listOfPlayerMissiles;

    static private ArrayList<BulletBlaster> listOfPlayerLasers;

    static private ArrayList<Bonus> listOfBonuses;

    static private ArrayList<Human> humans;

    static private double longestDistance = 2000;

    static private boolean missileOnScene = false;

    static private EnemyBoss boss;

    static private boolean isBossBattle;

    static private ArrayList<BulletBlaster> listOfBossBlasters;

    static private boolean firstBoss;

    static private SoundController soundController = new SoundController();

    private static final ModelManager ModelManagerInstance = new ModelManager();


    private ModelManager() {
        setObjects();
    }


    /*
    returns the instance of the model manager
     */
    public static ModelManager getInstance(){
        return ModelManagerInstance;
    }

    //initializes the model objects
    public void setObjects() {

        player = new Player();
        //initiating lists
        listOfPlayerBulletBlasters = new ArrayList<>();

        listOfEnemyShooterBullets = new ArrayList<>();

        listOfPlayerMissiles = new ArrayList<>();

        listOfPlayerLasers = new ArrayList<>();

        enemyShooters = new ArrayList<>();

        enemyKamikazes = new ArrayList<>();

        enemySplitters = new ArrayList<>();

        listOfBonuses = new ArrayList<>();

        humans = new ArrayList<>();

        isBossBattle = false;

        firstBoss = true;

        listOfBossBlasters = new ArrayList<>();

        ////////////////////////////////////
        boss = new EnemyBoss();
        boss.setSpawnLocation(1350);
        ////////////////////////////////////
        soundController.playBGM();
    }


    /*
    creates an enemy shooter
    checks if it should carry human (30% chance)
     */
    public void setEnemyShooter(){

        int carryingChance = (int) (Math.random() * ((10) + 1));

        EnemyShooter enemyShooter;

        if(carryingChance <= 3) {
            enemyShooter = new EnemyShooter(true);
        }
        else {
            enemyShooter = new EnemyShooter(false);
        }
        enemyShooter.setSpawnLocation(1350);
        enemyShooters.add(enemyShooter);
    }



    /*
    spawns kamikaze
     */
    public void setEnemyKamikaze(){
        EnemyKamikaze enemyKamikaze = new EnemyKamikaze();
        enemyKamikaze.setSpawnLocation(-50);
        enemyKamikazes.add(enemyKamikaze);
    }



    /*
    spawns enemy splitter
     */
    public void setEnemySplitter(){
        EnemySplitter enemySplitter = new EnemySplitter();
        enemySplitter.setSpawnLocation(1350);
        enemySplitters.add(enemySplitter);
    }



    /*
    spawns a new boss if enough enemies have been defeated
     */
    public void setBoss(){
        if(player.getEnemiesDestroyed()% 45 == 0 && !isBossBattle && player.getEnemiesDestroyed() != 0) {
            enemyShooters.clear();
            enemyKamikazes.clear();
            enemySplitters.clear();
            isBossBattle = true;
            //////////////////////////////////////////////
            boss.setSize(new Dimension(boss.getBOSS_WIDTH(), boss.getBOSS_HEIGHT()));
        }
        else if(boss.getHealth() < 1){
            boss.setHealth(200);
            boss.setSize(0,0);
            isBossBattle = false;
            player.updateEnemiesDestroyed();
        }
    }



    /*
    calls method to update enemy position and check if they are outside of scene
     */
    public void updatePositionOfEnemies(){
        setNewEnemyPosition();
        checkIfEnemiesOutOfScene();
    }



    /*
    calls the update method on all the enemies on the scene
     */
    private void setNewEnemyPosition(){
        if(!isBossBattle) {
            //update position of enemy shooters
            for (EnemyShooter enemyShooter : enemyShooters) {
                enemyShooter.updateMovement();
            }


            //updating position of kamikazes
            for (EnemyKamikaze enemyKamikaze : enemyKamikazes) {
                enemyKamikaze.setTarget(player);
                enemyKamikaze.updateMovement();
            }
            //updating position of splitters
            for (EnemySplitter enemySplitter : enemySplitters) {
                enemySplitter.updateMovement();
            }
        }

        //updating position of boss
        else {
            boss.updateMovement();
        }
    }


    /*
    removes the enemies if they are out of scene
     */
    private void checkIfEnemiesOutOfScene(){

        //check for enemy shooters
        ListIterator<EnemyShooter> listOfEnemyShipsIterator = enemyShooters.listIterator();
        while (listOfEnemyShipsIterator.hasNext())
        {
            Enemy enemyToCheck = listOfEnemyShipsIterator.next();
            if (enemyToCheck.getPosition().x < -50)
            {
                listOfEnemyShipsIterator.remove();
            }
        }


        //check for kamikaze
        ListIterator<EnemyKamikaze> kamikazeListIterator = enemyKamikazes.listIterator();
        while (kamikazeListIterator.hasNext())
        {
            EnemyKamikaze kamikazeToCheck = kamikazeListIterator.next();
            if(kamikazeToCheck.getPosition().x > 1350)
            {
                kamikazeListIterator.remove();
            }
        }

        //check for splitters
        ListIterator<EnemySplitter> splitterListIterator = enemySplitters.listIterator();
        while (splitterListIterator.hasNext())
        {
            EnemySplitter splitterToCheck = splitterListIterator.next();
            if(splitterToCheck.getPosition().x < -50)
            {
                splitterListIterator.remove();
            }
        }
    }



    /*
    checks if the enemies are in a collision with the given player
     */
    public void checkIfEnemyInCollision(Player player){

        if(!isBossBattle) {

            //checking for shooters collisions
            for (int i = 0; i < enemyShooters.size(); i++) {
                EnemyShooter currEnemy = enemyShooters.get(i);
                Rectangle enemyRectangle = currEnemy.getBounds();

                //only if player and checked enemy are on each other
                if (player.intersects(enemyRectangle)) {
                    //deducting enemy health
                    currEnemy.setHealth(currEnemy.getHealth() - 10);

                    //update player stats
                    if (!player.getIsShielded()) {
                        player.setHealth(player.getHealth() - 10);
                    }
                }
            }
            //checking for kamikazes collisions
            for (int i = 0; i < enemyKamikazes.size(); i++) {
                EnemyKamikaze currEnemy = enemyKamikazes.get(i);
                Rectangle enemyRectangle = currEnemy.getBounds();

                //only if player and checked enemy are on each other
                if (player.intersects(enemyRectangle)) {
                    //deducting enemy health
                    currEnemy.setHealth(currEnemy.getHealth() - 10);

                    //update player stats
                    if (!player.getIsShielded()) {
                        player.setHealth(player.getHealth() - 10);
                    }
                }
            }

            //checking for splitters collision
            for (int i = 0; i < enemySplitters.size(); i++) {
                EnemySplitter currEnemy = enemySplitters.get(i);
                Rectangle enemyRectangle = currEnemy.getBounds();

                //only if player and checked enemy are on each other
                if (player.intersects(enemyRectangle)) {
                    //deducting enemy health
                    currEnemy.setHealth(currEnemy.getHealth() - 10);

                    //update player stats
                    if (!player.getIsShielded()) {
                        player.setHealth(player.getHealth() - 10);
                    }
                }
            }
        }
        else {

            Rectangle enemyRectangle = boss.getBounds();

            //only if player and checked enemy are on each other
            if (player.intersects(enemyRectangle)) {
                //deducting enemy health
                boss.setHealth(boss.getHealth() - 10);

                //update player stats
                if (!player.getIsShielded()) {
                    player.setHealth(player.getHealth() - 10);
                }
            }
        }
    }


    /**
     * checks of the enemies health is reduces to zero and removes them
     * checks of the enemies were carrying humans and spawns the humans
     * checks if the player has destroyed enough enemies and spawns a random type of bonus
     */
    public void checkIfEnemyDestroyed(){
        if(!isBossBattle) {
            //checking for shooters
            for (int i = 0; i < enemyShooters.size(); i++) {
                if (enemyShooters.get(i).getHealth() < 1) {
                    if (enemyShooters.get(i).getIsCarryingHuman()) {   //spawns a human if the ship was carrying it
                        Human human = new Human();
                        human.setSpawnLocation(enemyShooters.get(i).getLocation().x, enemyShooters.get(i).getLocation().y);
                        humans.add(human);
                    }

                    //updates players kills
                    player.updateEnemiesDestroyed();

                    //checks whether to spawn bonus
                    checkSpawnBonus(enemyShooters.get(i));

                    //setBonus(enemyShooters.get(i));
                    enemyShooters.remove(i);
                    player.setScore(player.getScore() + 10);
                    soundController.playSound("explosion_enemy", -5.0f);
                }
            }

            //checking for kamikazes
            for (int i = 0; i < enemyKamikazes.size(); i++) {
                if (enemyKamikazes.get(i).getHealth() < 1) {

                    //updates players kills
                    player.updateEnemiesDestroyed();

                    //checks whether to spawn bonus
                    checkSpawnBonus(enemyKamikazes.get(i));
                    enemyKamikazes.remove(i);
                    player.setScore(player.getScore() + 15);
                    soundController.playSound("explosion_enemy", -5.0f);
                }
            }

            //checking for splitters
            for (int i = 0; i < enemySplitters.size(); i++) {
                if (enemySplitters.get(i).getHealth() < 1) {
                    //updates players kills
                    player.updateEnemiesDestroyed();

                    //checks whether to spawn bonus
                    checkSpawnBonus(enemySplitters.get(i));

                    //splits into shooters or kamikazes
                    Random random = new Random();
                    int xPos = enemySplitters.get(i).getLocation().x;
                    int yPos = enemySplitters.get(i).getLocation().y;
                    if (random.nextInt(2) == 0) {
                        for (int j = -1; j <= 1; j += 2) {
                            EnemyShooter enemyShooter = new EnemyShooter(false);
                            enemyShooter.setLocation(xPos, yPos + j * 50);
                            enemyShooters.add(enemyShooter);
                        }
                    } else {
                        EnemyKamikaze enemyKamikaze = new EnemyKamikaze();
                        enemyKamikaze.setLocation(xPos, yPos);
                        enemyKamikazes.add(enemyKamikaze);
                    }
                    enemySplitters.remove(i);
                    player.setScore(player.getScore() + 15);
                    soundController.playSound("explosion_enemy", -5.0f);
                }
            }
        }
        else{
            if(boss.getHealth() == 0){
                player.setScore(player.getScore() + 200);
                soundController.playSound("explosion_enemy", -5.0f);
            }
        }
    }


//--------------------bonus-------------------------------------
    public void checkSpawnBonus(Enemy enemy){
        if(player.getEnemiesDestroyed() % 5 == 0 ){
            Bonus bonus = null;
            int ran = (int) (Math.random() * ((4 - 1) + 1)) + 1;


            if (ran == 1) {
                bonus = new BulletBonus();

            }

            else if (ran == 2){
                bonus = new HealthBonus();
            }

            else if (ran == 3){
                bonus = new ShieldBonus();
            }

            else {
                bonus = new SpeedBonus();
            }

            if(bonus != null) {
                listOfBonuses.add(bonus);
                setBonusStartPosition(bonus, enemy);
            }
        }
    }



//--------------------humans----------------------------------------------------

    //controls the human falling
    public void updateHumanMovement(){
        for (Human currHuman : humans)
            currHuman.updateMovement();
    }


    //checks of the player has collided with the humans
    public void checkIfHumansAndBonusInCollision(){
        //checking for bonus
        for(int i = 0; i < listOfBonuses.size(); i++)
        {
            Bonus currBonus = listOfBonuses.get(i);
            Rectangle bonusRectangle = currBonus.getBounds();

            if (player.intersects(bonusRectangle))
            {
                player.setBonusUpgrade(listOfBonuses.get(i));
                listOfBonuses.remove(i);
                soundController.playSound("bonus", -5.0f);
            }
        }

        //checking for humans
        for(int i = 0; i < humans.size(); i++)
        {
            Human currHuman = humans.get(i);
            Rectangle humanRectangle = currHuman.getBounds();

            if (player.intersects(humanRectangle))
            {
                player.setScore(player.getScore() + 100);
                humans.remove(i);
                player.setHumanAboard(player.getHumanAboard()+1);
            }
        }


    }

//------------------end humans----------------------------------------------------






//------------------missile-----------------------------------


    //(ONLY FOR MISSILES) sets true if player shoots missiles and they are present on a scene
    public void setIfContainsMissiles(boolean ifContainsMissiles)
    {
        ModelManager.missileOnScene = ifContainsMissiles;
    }


    /*(ONLY FOR MISSILES) checks if given bullet is a MISSILE, update status if yes;
     * it's used to determine if they are MISSILES on a scene or not*/
    public void checkIfMissilesOnScene()
    {
        if (listOfPlayerMissiles.size() > 0)
        {
            setIfContainsMissiles(true);
        }
    }


    //assign closest Enemy object to a Missile
    public void setClosestEnemyToMissile()
    {
        //trigger only if missiles are on a list and they are enemies on a scene
        if ((enemyShooters.size()>0 || enemyKamikazes.size() >0 || enemySplitters.size() > 0) && missileOnScene)
        {
            //reset information about missiles on scene, will determine if they are there inside a loop
            setIfContainsMissiles(false);

            for (BulletMissile bullet : listOfPlayerMissiles)
            {
                //sets closest enemy to a MISSILE bullet
                defineClosestEnemy(bullet);

                //update information about missiles on scene here;
                // * if one of bullets on a list is a MISSILE, then loop will be activated on next timer tick;
                // * otherwise, it won't be triggered
                checkIfMissilesOnScene();
            }
        }
    }



    public void defineClosestEnemy(BulletMissile bullet)
    {

        //lock on to enemies other than boss
        if(!isBossBattle) {
            //iterate through list of Enemies and find closest one
            for (Enemy enemy : enemyShooters) {
                checkDistanceToEnemy(bullet, enemy);
            }

            for (Enemy enemy : enemyKamikazes) {
                checkDistanceToEnemy(bullet, enemy);
            }

            for (Enemy enemy : enemySplitters) {
                checkDistanceToEnemy(bullet, enemy);
            }
        }

        //locks on to boss if it is present
        else {
            checkDistanceToEnemy(bullet, boss);
        }

        //resets changes
        longestDistance = 2000;
    }


    private void checkDistanceToEnemy(BulletMissile bullet, Enemy enemy)
    {

        double distanceToCheck = Math.sqrt(Math.pow(bullet.getCenterX() - enemy.getCenterX(), 2) +
                Math.pow(bullet.getCenterY() - enemy.getCenterY(), 2));

        //sets target Enemy if calculated distance is smaller
        if (longestDistance > distanceToCheck)
        {
            longestDistance = distanceToCheck;
            bullet.setTarget(enemy);
        }

    }


//-----------------end missile------------------------------------------------





    //checks if player and enemy bullets and also humans are out of scene
    public void checkIfBulletsAndHumansOutOfScene() {

        //-------------------checking for player bullets-------------------------

        //checking for bullet blasters
        ListIterator<BulletBlaster> listOfPlayerBulletBlastersIterator = listOfPlayerBulletBlasters.listIterator();
        while (listOfPlayerBulletBlastersIterator.hasNext()) {
            //remove if is out of one border
            BulletBlaster bulletToCheck = listOfPlayerBulletBlastersIterator.next();
            if (bulletToCheck.getLocation().y > 820 || bulletToCheck.getLocation().y < 0
                    || bulletToCheck.getLocation().x > 1300 || bulletToCheck.getLocation().x < 0) {
                listOfPlayerBulletBlastersIterator.remove();
            }
        }

        //checking for missiles
        ListIterator<BulletMissile> listOfPlayerBulletMissileIterator = listOfPlayerMissiles.listIterator();
        while (listOfPlayerBulletMissileIterator.hasNext()) {
            //remove if is out of one border
            BulletMissile bulletToCheck = listOfPlayerBulletMissileIterator.next();
            if (bulletToCheck.getLocation().y > 820 || bulletToCheck.getLocation().y < 0
                    || bulletToCheck.getLocation().x > 1300 || bulletToCheck.getLocation().x < 0) {
                listOfPlayerBulletMissileIterator.remove();
            }
        }

        //checking for lasers
        ListIterator<BulletBlaster> listOfPlayerBulletLaserIterator = listOfPlayerLasers.listIterator();
        while (listOfPlayerBulletLaserIterator.hasNext()) {
            //remove if is out of one border
            BulletBlaster bulletToCheck = listOfPlayerBulletLaserIterator.next();
            if (bulletToCheck.getLocation().y > 820 || bulletToCheck.getLocation().y < 0
                    || bulletToCheck.getLocation().x > 1300 || bulletToCheck.getLocation().x < 0) {
                listOfPlayerBulletLaserIterator.remove();
            }
        }


        //--------------------------checking for enemy bullets------------------------------
        //checking for bullet blasters for enemies
        ListIterator<BulletBlaster> listOfEnemyBlasterIterator = listOfEnemyShooterBullets.listIterator();
        while (listOfEnemyBlasterIterator.hasNext()) {
            //remove if is out of one border
            BulletBlaster bulletToCheck = listOfEnemyBlasterIterator.next();
            if (bulletToCheck.getLocation().y > 820 || bulletToCheck.getLocation().y < 0
                    || bulletToCheck.getLocation().x > 1350 || bulletToCheck.getLocation().x < -60) {
                listOfEnemyBlasterIterator.remove();
            }
        }


        //-----------------------checking for humans------------------------------------------
        //checking for bullet blasters for enemies
        ListIterator<Human> listOfHumansIterator = humans.listIterator();
        while (listOfHumansIterator.hasNext()) {
            //remove if is out of one border
            Human humanToCheck = listOfHumansIterator.next();
            if (humanToCheck.getLocation().y > 820) {
                listOfHumansIterator.remove();
            }
        }

    }


    //sets start position for player bullet
    private void setPlayerBulletStartPosition(Bullet bullet, int deltaX, int deltaY) {
        bullet.setLocation(player.getCenter().x + deltaX, player.getCenter().y + deltaY);
    }


    //sets start position of enemy shooter bullets
    private void setEnemyShooterBulletStartPosition(Enemy enemy, Bullet bullet, int deltaX, int deltaY){
        bullet.setLocation(enemy.getCenter().x + deltaX, enemy.getCenter().y + deltaY);
    }

    // sets starting position of the bonus //////////////////////////////////////////////////////
    private void setBonusStartPosition(Bonus bonus, Enemy enemy)
    {
        bonus.setLocation(enemy.getCenter().x, enemy.getCenter().y);
    }


    //set Blaster Bullets of Player
    public void setPlayerBlasterBullets() {
        //sets new blaster bullets-> max number of bullets in one series is 7
        BulletBlaster newBullet;

        if(player.getIsRight()){
            newBullet = new BulletBlaster(true);
            //bulletsModel.setType(newBullet, "BLASTER");
            newBullet.setBulletSize(new Dimension(6, 6));
            newBullet.setSpeed(14);

            //setting images
            newBullet.setImage("/bullet_right.png");
            newBullet.setSize(new Dimension(30, 10));
            //bulletsModel.setPower(newBullet, playerModel.getInfo(player, "powerBlaster"));


            setPlayerBulletStartPosition(newBullet, 50, 0);
        }
        else {
            newBullet = new BulletBlaster(false);
            //bulletsModel.setType(newBullet, "BLASTER");
            newBullet.setBulletSize(new Dimension(6, 6));
            newBullet.setSpeed(-14);

            //setting images
            newBullet.setImage("/bullet_left.png");
            newBullet.setSize(new Dimension(30, 10));
            //bulletsModel.setPower(newBullet, playerModel.getInfo(player, "powerBlaster"));


            setPlayerBulletStartPosition(newBullet, -70, 0);
        }

        //adds to list
        listOfPlayerBulletBlasters.add(newBullet);
        soundController.playSound("bullet_blaster", -10.0f);

    }


    //sets the missiles for the player
    public void setPlayerMissile(){
        //only creates the missile if there is an enemy on the scene
        if(player.getRemainingMissiles() > 0 && (enemyShooters.size() > 0 || isBossBattle)) { //----do check this later------------
            BulletMissile newBullet;

            if (player.getIsRight()) {
                newBullet = new BulletMissile(true);
                //bulletsModel.setType(newBullet, "BLASTER");
                newBullet.setBulletSize(new Dimension(6, 6));
                newBullet.setSpeed(9);

                //setting images
                newBullet.setImage("/missile_right.png");
                newBullet.setSize(new Dimension(40, 10));
                //bulletsModel.setPower(newBullet, playerModel.getInfo(player, "powerBlaster"));


                setPlayerBulletStartPosition(newBullet, 50, 0);
            } else {
                newBullet = new BulletMissile(false);
                //bulletsModel.setType(newBullet, "BLASTER");
                newBullet.setBulletSize(new Dimension(6, 6));
                newBullet.setSpeed(9);

                //setting images
                newBullet.setImage("/missile_left.png");
                newBullet.setSize(new Dimension(40, 10));
                //bulletsModel.setPower(newBullet, playerModel.getInfo(player, "powerBlaster"));


                setPlayerBulletStartPosition(newBullet, -70, 0);
            }

            //adds to list
            listOfPlayerMissiles.add(newBullet);
            soundController.playSound("missile", -5.0f);
            //sets true-> missiles on a scene
            setIfContainsMissiles(true);

            //sets target for missiles
            defineClosestEnemy(newBullet);

            player.updateRemainingMissiles(); //decrements the number of missiles the player has
        }
    }

    //sets the lasers for the player
    public void setPlayerLaser() {
        if(player.getRemainingLasers() > 0) {
            BulletBlaster newBullet;

            if (player.getIsRight()) {
                newBullet = new BulletBlaster(true);
                //bulletsModel.setType(newBullet, "BLASTER");
                newBullet.setBulletSize(new Dimension(6, 6));
                newBullet.setSpeed(14);

                //setting images
                newBullet.setImage("/spaceship_laser.png");
                newBullet.setSize(new Dimension(30, 10));
                //bulletsModel.setPower(newBullet, playerModel.getInfo(player, "powerBlaster"));


                setPlayerBulletStartPosition(newBullet, 50, 0);
                player.updateRemainingLasers();
            } else {
                newBullet = new BulletBlaster(false);
                //bulletsModel.setType(newBullet, "BLASTER");
                newBullet.setBulletSize(new Dimension(6, 6));
                newBullet.setSpeed(-14);

                //setting images
                newBullet.setImage("/spaceship_laser.png");
                newBullet.setSize(new Dimension(30, 10));
                //bulletsModel.setPower(newBullet, playerModel.getInfo(player, "powerBlaster"));


                setPlayerBulletStartPosition(newBullet, -70, 0);
                player.updateRemainingLasers();
            }

            //adds to list
            listOfPlayerLasers.add(newBullet);
            player.updateRemainingLasers();
            soundController.playSound("laser_player", -5.0f);
        }
    }



    //sets the bullets of the enemy
    public void setEnemyBlasterBullets(){

        if(!isBossBattle) {

            if (enemyShooters.size() > 0) {

                int num = (int) (Math.random() * ((enemyShooters.size() - 1) + 1));
                if (enemyShooters.get(num).getLocation().x > 50) {

                    BulletBlaster newBullet = new BulletBlaster(false);

                    //bulletsModel.setType(newBullet, "BLASTER");
                    newBullet.setBulletSize(new Dimension(6, 6));
                    newBullet.setSpeed(-10);

                    //setting images
                    newBullet.setImage("/enemy_laser.png");
                    newBullet.setSize(new Dimension(30, 10));
                    //bulletsModel.setPower(newBullet, playerModel.getInfo(player, "powerBlaster"));

                    setEnemyShooterBulletStartPosition(enemyShooters.get(num), newBullet, 0, 0);

                    //adds to list
                    listOfEnemyShooterBullets.add(newBullet);
                    soundController.playSound("laser_player", -5.0f);
                }
            }

            if (enemySplitters.size() > 0) {

                int num = (int) (Math.random() * ((enemySplitters.size() - 1) + 1));
                if (enemySplitters.get(num).getLocation().x > 50) {
                    BulletBlaster newBullet = new BulletBlaster(false);

                    //bulletsModel.setType(newBullet, "BLASTER");
                    newBullet.setBulletSize(new Dimension(6, 6));
                    newBullet.setSpeed(-10);

                    //setting images
                    newBullet.setImage("/enemy_laser.png");
                    newBullet.setSize(new Dimension(30, 10));
                    //bulletsModel.setPower(newBullet, playerModel.getInfo(player, "powerBlaster"));

                    setEnemyShooterBulletStartPosition(enemySplitters.get(num), newBullet, 0, 0);

                    //adds to list
                    listOfEnemyShooterBullets.add(newBullet);
                    soundController.playSound("laser_player", -5.0f);
                }
            }
        }
        else {
            for(int i = -50; i <= 50; i+=50){
                BulletBlaster newBullet = new BulletBlaster(false);
                //bulletsModel.setType(newBullet, "BLASTER");
                newBullet.setBulletSize(new Dimension(6, 6));
                newBullet.setSpeed(-4);

                //setting images
                newBullet.setImage("/enemy_laser.png");
                newBullet.setSize(new Dimension(30, 10));
                //bulletsModel.setPower(newBullet, playerModel.getInfo(player, "powerBlaster"));

                setEnemyShooterBulletStartPosition(boss, newBullet, 0, i);

                //adds to list
                listOfEnemyShooterBullets.add(newBullet);
                soundController.playSound("boss_laser", -15.0f);
            }
        }


    }





    //updates the position of player and enemy bullets(calls the appropriate methods
    public void setNewPositionOfBullets() {
        //updating enemy bullets
        updatePositionOfEnemyBullets();

        //update player bullets
        updatePositionOfPlayerBullets();

        //checking if bullets out of scene
        checkIfBulletsAndHumansOutOfScene();
    }


    //update position of enemy bullets
    public void updatePositionOfEnemyBullets(){
        //updating blasters position
        ListIterator<BulletBlaster> bulletIterator = listOfEnemyShooterBullets.listIterator();
        while (bulletIterator.hasNext()) {
            BulletBlaster bulletToMove = bulletIterator.next();

            //updatePositionAsBlaster(bulletToMove);
            bulletToMove.calculateMovement();

        }
    }

    //update position of player blaster bullets
    public void updatePositionOfPlayerBullets() {

        //updating blasters position
        ListIterator<BulletBlaster> bulletIterator = listOfPlayerBulletBlasters.listIterator();
        while (bulletIterator.hasNext()) {
            BulletBlaster bulletToMove = bulletIterator.next();

            bulletToMove.calculateMovement();

        }

        //updating missile position
        ListIterator<BulletMissile> missileIterator = listOfPlayerMissiles.listIterator();
        while (missileIterator.hasNext()) {
            BulletMissile bulletToMove = missileIterator.next();

            bulletToMove.calculateMovement();
        }

        //update laser position
        ListIterator<BulletBlaster> laserIterator = listOfPlayerLasers.listIterator();
        while (laserIterator.hasNext()) {
            BulletBlaster bulletToMove = laserIterator.next();

            //updatePositionAsBlaster(bulletToMove);
            bulletToMove.calculateMovement();
        }
    }


    //checks if the player bullets are colliding with the enemies
    public void checkIfPlayerBulletsInCollision(){

        if(!isBossBattle) {
            //checking for blasters
            for (int i = 0; i < listOfPlayerBulletBlasters.size(); i++) {
                BulletBlaster currBullet = listOfPlayerBulletBlasters.get(i);
                for (int j = 0; j < enemyShooters.size(); j++) {
                    EnemyShooter currEnemy = enemyShooters.get(j);
                    Rectangle enemyRectangle = currEnemy.getBounds();
                    if (currBullet.intersects(enemyRectangle)) {

                        currEnemy.setHealth(currEnemy.getHealth() - currBullet.getDamage());

                        listOfPlayerBulletBlasters.remove(i);

                    }
                }

                for (int j = 0; j < enemyKamikazes.size(); j++) {
                    EnemyKamikaze currEnemy = enemyKamikazes.get(j);
                    Rectangle enemyRectangle = currEnemy.getBounds();
                    if (currBullet.intersects(enemyRectangle)) {

                        currEnemy.setHealth(currEnemy.getHealth() - currBullet.getDamage());

                        listOfPlayerBulletBlasters.remove(i);

                    }
                }

                for (int j = 0; j < enemySplitters.size(); j++) {
                    EnemySplitter currEnemy = enemySplitters.get(j);
                    Rectangle enemyRectangle = currEnemy.getBounds();
                    if (currBullet.intersects(enemyRectangle)) {

                        currEnemy.setHealth(currEnemy.getHealth() - currBullet.getDamage());

                        listOfPlayerBulletBlasters.remove(i);
                    }
                }
            }


            //checking for missiles
            for (int i = 0; i < listOfPlayerMissiles.size(); i++) {
                BulletMissile currBullet = listOfPlayerMissiles.get(i);
                for (int j = 0; j < enemyShooters.size(); j++) {
                    EnemyShooter currEnemy = enemyShooters.get(j);
                    Rectangle enemyRectangle = currEnemy.getBounds();
                    if (currBullet.intersects(enemyRectangle)) {

                        currEnemy.setHealth(currEnemy.getHealth() - currBullet.getDamage());

                        listOfPlayerMissiles.remove(i);

                    }
                }

                for (int j = 0; j < enemyKamikazes.size(); j++) {
                    EnemyKamikaze currEnemy = enemyKamikazes.get(j);
                    Rectangle enemyRectangle = currEnemy.getBounds();
                    if (currBullet.intersects(enemyRectangle)) {

                        currEnemy.setHealth(currEnemy.getHealth() - currBullet.getDamage());

                        listOfPlayerMissiles.remove(i);

                    }
                }

                for (int j = 0; j < enemySplitters.size(); j++) {
                    EnemySplitter currEnemy = enemySplitters.get(j);
                    Rectangle enemyRectangle = currEnemy.getBounds();
                    if (currBullet.intersects(enemyRectangle)) {

                        currEnemy.setHealth(currEnemy.getHealth() - currBullet.getDamage());

                        listOfPlayerMissiles.remove(i);
                    }
                }
            }


            //checking for Lasers
            for (int i = 0; i < listOfPlayerLasers.size(); i++) {
                BulletBlaster currBullet = listOfPlayerLasers.get(i);
                for (int j = 0; j < enemyShooters.size(); j++) {
                    EnemyShooter currEnemy = enemyShooters.get(j);
                    Rectangle enemyRectangle = currEnemy.getBounds();
                    if (currBullet.intersects(enemyRectangle)) {

                        currEnemy.setHealth(currEnemy.getHealth() - currBullet.getDamage());


                    }
                }

                for (int j = 0; j < enemyKamikazes.size(); j++) {
                    EnemyKamikaze currEnemy = enemyKamikazes.get(j);
                    Rectangle enemyRectangle = currEnemy.getBounds();
                    if (currBullet.intersects(enemyRectangle)) {

                        currEnemy.setHealth(currEnemy.getHealth() - currBullet.getDamage());


                    }
                }

                for (int j = 0; j < enemySplitters.size(); j++) {
                    EnemySplitter currEnemy = enemySplitters.get(j);
                    Rectangle enemyRectangle = currEnemy.getBounds();
                    if (currBullet.intersects(enemyRectangle)) {

                        currEnemy.setHealth(currEnemy.getHealth() - currBullet.getDamage());
                    }
                }
            }
        }

        //bullet collision is only checked against boss if there is a boss battle
        else {
            Rectangle enemyRectangle = boss.getBounds();
            //checking for blasters
            for(int i = 0; i < listOfPlayerBulletBlasters.size(); i++){
                BulletBlaster currBullet = listOfPlayerBulletBlasters.get(i);
                if (currBullet.intersects(enemyRectangle)) {
                    boss.setHealth(boss.getHealth() - currBullet.getDamage());
                    listOfPlayerBulletBlasters.remove(i);
                }
            }

            //checking for lasers
            for(int i = 0; i < listOfPlayerLasers.size(); i++){
                BulletBlaster currBullet = listOfPlayerLasers.get(i);
                if (currBullet.intersects(enemyRectangle)) {
                    boss.setHealth(boss.getHealth() - currBullet.getDamage());
                }
            }

            //checking for missiles
            for(int i = 0; i < listOfPlayerMissiles.size(); i++){
                BulletMissile currBullet = listOfPlayerMissiles.get(i);
                if (currBullet.intersects(enemyRectangle)) {
                    boss.setHealth(boss.getHealth() - currBullet.getDamage());
                    listOfPlayerMissiles.remove(i);
                }
            }
        }
    }



    //checks if enemy bullets are in collision with the player
    public void checkIfEnemyBulletsInCollision(){
        //checking for blasters
        for(int i = 0; i < getListOfEnemyShooterBullets().size(); i++){
            BulletBlaster currBullet = listOfEnemyShooterBullets.get(i);

            Rectangle playerRectangle = player.getBounds();
            if(currBullet.intersects(playerRectangle)){

                if(!player.getIsShielded()) {
                    player.setHealth(player.getHealth() - currBullet.getDamage());
                }

                listOfEnemyShooterBullets.remove(i);

                if(player.getHealth() <= 0) {
                    soundController.playSound("explosion_player", -5.0f);
                }
            }
        }
    }




    //-------------------------------getters--------------------------------
    //gets player object
    public Player getPlayer() {
        return player;
    }


    public ArrayList<BulletBlaster> getListOfPlayerBulletBlasters() {
        return listOfPlayerBulletBlasters;
    }

    public ArrayList<EnemyShooter> getEnemyShooters(){
        return enemyShooters;
    }

    public ArrayList<EnemyKamikaze> getEnemyKamikazes(){
        return enemyKamikazes;
    }

    public ArrayList<EnemySplitter> getEnemySplitters(){
        return enemySplitters;
    }

    public ArrayList<BulletBlaster> getListOfEnemyShooterBullets(){
        return listOfEnemyShooterBullets;
    }

    public ArrayList<BulletMissile> getListOfPlayerMissiles(){
        return listOfPlayerMissiles;
    }

    public ArrayList<BulletBlaster> getListOfPlayerLasers(){
        return listOfPlayerLasers;
    }

    public ArrayList<Bonus> getListOfBonuses(){return listOfBonuses;}

    public ArrayList<Human> getListOfHumans(){return humans;}

    public EnemyBoss getBoss(){return boss;}

    public boolean getIsBossBattle(){return isBossBattle;}
}