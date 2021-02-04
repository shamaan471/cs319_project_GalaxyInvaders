package View;

import Model.Bonus.*;
import Model.Enemy.*;
import Model.Bullet.*;
import Model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GameSceneView extends JPanel {

    static private JPanel gameScenes;  //panel on which the actual game will take place
    static private JPanel labelContainer; //to contain JLabels such as score, health

    static private BufferedImage gameSceneBackgroundImage;
    static private BufferedImage healthIcon;
    static private BufferedImage scoreIcon;
    static private BufferedImage humanIcon;
    static private BufferedImage laserIcon;
    static private BufferedImage missleIcon;

    static private JLabel healthLabel;
    static private JLabel scoreLabel;
    static private JLabel humanLabel;
    static private JLabel laserLabel;
    static private JLabel missileLabel;

    static private Player player;

    static private ArrayList<BulletBlaster> listOfPlayerBulletBlasters;

    static private ArrayList<EnemyShooter> listOfEnemyShooters;

    static private ArrayList<BulletBlaster> listOfEnemyShooterBlasters;

    static private ArrayList<EnemyKamikaze> listOfEnemyKamikazes;

    static private ArrayList<EnemySplitter> listOfEnemySplitters;

    static private ArrayList<BulletMissile> listOfPlayerMissiles;

    static private ArrayList<BulletBlaster> listOfPlayerLasers;

    static private ArrayList<Bonus> listOfBonuses;

    static private ArrayList<Human> listOfHumans;

    static private EnemyBoss boss;

    private int time = 0;

    public GameSceneView(){
        setLayout(new BorderLayout());


        setImage();
        player = new Player();
        setComponents();
        addComponents();

        //setBackground(new Color(41, 37, 37));

        //setOpaque(false);

        boss = new EnemyBoss();

    }


    public void setComponents(){
        labelContainer = new JPanel( new GridLayout(1,5));
        labelContainer.setOpaque(false);

        gameScenes = new JPanel();
        gameScenes.setOpaque(false);
        setImageLabel();
        humanLabel = new JLabel("Humans Rescued: " + 0);
        scoreLabel = new JLabel("Score: " + 0);
        healthLabel = new JLabel("Health: " + 100);
        laserLabel = new JLabel("Laser: " + 0);
        missileLabel= new JLabel("Missile: " + 0);

        healthLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 18));
        healthLabel.setForeground(Color.GREEN);

        scoreLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 18));
        scoreLabel.setForeground(Color.WHITE);

        humanLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 18));
        humanLabel.setForeground(Color.RED);

        laserLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 18));
        laserLabel.setForeground(Color.BLUE);

        missileLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 18));
        missileLabel.setForeground(Color.CYAN);


        labelContainer.add(healthLabel);
        labelContainer.add(scoreLabel);
        labelContainer.add(humanLabel);
        labelContainer.add(laserLabel);
        labelContainer.add(missileLabel);

        //initializing player bullets
        listOfPlayerBulletBlasters = new ArrayList<>();
        listOfPlayerMissiles = new ArrayList<>();
        listOfPlayerLasers = new ArrayList<>();

        //initializing enemies
        listOfEnemyShooters = new ArrayList<>();
        listOfEnemyKamikazes = new ArrayList<>();
        listOfEnemySplitters = new ArrayList<>();

        //initializing enemy bullets
        listOfEnemyShooterBlasters = new ArrayList<>();

        //initializing bonuses
        listOfBonuses = new ArrayList<>();

        //initializing humans
        listOfHumans = new ArrayList<>();

    }

    public void addComponents(){
        add(gameScenes, BorderLayout.CENTER);

        add(labelContainer, BorderLayout.NORTH);
    }


    public void setImage(){
        try {
            //sets image to given picture path
            gameSceneBackgroundImage = ImageIO.read(getClass().getResourceAsStream("/bimage.jpg"));

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setImageLabel() {
        try {
            healthIcon = ImageIO.read(getClass().getResourceAsStream("/health_icon.png"));
            scoreIcon = ImageIO.read(getClass().getResourceAsStream("/score_icon.png"));
            humanIcon = ImageIO.read(getClass().getResourceAsStream("/human.png"));
            laserIcon = ImageIO.read(getClass().getResourceAsStream("/laserIcon.png"));
            missleIcon = ImageIO.read(getClass().getResourceAsStream("/missileIcon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //sets the list of player bullet blasters
    public void updateListOfPlayerBulletBlasters(ArrayList<BulletBlaster> listOfPlayerBullets)
    {
        GameSceneView.listOfPlayerBulletBlasters = listOfPlayerBullets;
    }


    //sets the list of bonuses

    public void updateListOfBonuses(ArrayList<Bonus> listOfBonuses)
    {
        GameSceneView.listOfBonuses = listOfBonuses;
    }



    //sets the list of enemy shooters
    public void updateListOfEnemyShooters(ArrayList<EnemyShooter> listOfEnemyShooters){
        GameSceneView.listOfEnemyShooters = listOfEnemyShooters;
    }


    //sets the list of enemy shooter blasters
    public void updateListOfEnemyShooterBlasters(ArrayList<BulletBlaster> listOfEnemyShooterBlasters){
        GameSceneView.listOfEnemyShooterBlasters = listOfEnemyShooterBlasters;
    }

    //sets the list of enemy kamikazes
    public void updateListOfEnemyKamikazes(ArrayList<EnemyKamikaze> listOfEnemyKamikazes){
        GameSceneView.listOfEnemyKamikazes = listOfEnemyKamikazes;
    }

    //sets the list of enemy splitters
    public void updateListOfEnemySplitters(ArrayList<EnemySplitter> listOfEnemySplitters){
        GameSceneView.listOfEnemySplitters = listOfEnemySplitters;
    }

    //sets the player
    public void setPlayer (Player player)
    {
        GameSceneView.player = player;
    }
    //sets the list of player missiles
    public void updateListOfPlayerMissiles(ArrayList<BulletMissile> listOfPlayerMissiles){
        GameSceneView.listOfPlayerMissiles = listOfPlayerMissiles;
    }

    //sets the list of player lasers
    public void updateListOfPlayerLasers(ArrayList<BulletBlaster> listOfPlayerLasers){
        GameSceneView.listOfPlayerLasers = listOfPlayerLasers;
    }


    //sets the list of humans to paint
    public void updateListOfHumans(ArrayList<Human> listOfHumans){
        GameSceneView.listOfHumans = listOfHumans;
    }


    //sets the boss
    public void setBoss(EnemyBoss boss){
        GameSceneView.boss = boss;
    }


    //pops up the JOption pane to show the player that the game is over
    public void popGameOver(){
        JOptionPane.showMessageDialog(null, "Your Ship is Destroyed!\nYour Score: " + player.getScore(),
                "GameOver!", JOptionPane.CLOSED_OPTION);
    }



    //pops up game completion message of boss defeated
    public void popGameWon(){
        JOptionPane.showMessageDialog(null, "You defeated the boss!\nYour Score: " + player.getScore(),
                "The World Is Saved!", JOptionPane.CLOSED_OPTION);
    }

    //sets the list of humans to paint
    public void updateScore(int score){
        scoreLabel.setText("Score: " + score);
    }
    public void updateHealth(int health) {
        if(health >= 0)
            healthLabel.setText("       Health: "+ health);
    }
    public void updateHuman(int humanAboard)
    {
        humanLabel.setText("Human Rescued: "+ humanAboard);
    }
    public void updateLaser(int count){
        laserLabel.setText("Laser: " + count);
    }
    public void updateMissile(int count){
        missileLabel.setText("Missile: " + count);
    }




    @Override public void paintComponent(Graphics g)
    {
        super.paintComponent(g);


        //g.drawImage(gameSceneBackgroundImage, getX(), getY(),getSize().width, getSize().height,null);
        time =time+9;

        /////////////////////////////////////////////////////////
        //MIGHT CAUSE CRASH!!!!!!!!!!!!!!!!!!!!!!!1
        /////////////////////////////////////////////////////////
        g.drawImage(gameSceneBackgroundImage, getX() - time, getY(),
                getSize().width, getSize().height, null);

        if(getX()-time<=-10) {
            //time = 0;
            g.drawImage(gameSceneBackgroundImage, getSize().width-time, getY(),
                    getSize().width, getSize().height, null);

        }
        if(time>=1274)
        {

            time=0;
            g.drawImage(gameSceneBackgroundImage, getSize().width-time, getY(),
                    getSize().width, getSize().height, null);
        }

        g.drawImage(healthIcon, healthLabel.getX(), healthLabel.getY(),25, 42,null);
        g.drawImage(scoreIcon, scoreLabel.getX()-20, scoreLabel.getY(),18, 18,null);
        g.drawImage(humanIcon, humanLabel.getX()-20, humanLabel.getY(),10, 18,null);
        g.drawImage(laserIcon, laserLabel.getX()-20, laserLabel.getY(),17, 13,null);
        g.drawImage(missleIcon, missileLabel.getX()-20, missileLabel.getY(),15, 25,null);
        //paint player
        g.drawImage(player.getPlayerImage(), player.getLocation().x, player.getLocation().y,
                player.getSize().width, player.getSize().height, null);



        //paint player blaster bullets
        for (BulletBlaster bulletBlaster : listOfPlayerBulletBlasters)
        {
            g.drawImage(bulletBlaster.getImage(), bulletBlaster.getLocation().x, bulletBlaster.getLocation().y,
                    bulletBlaster.getSize().width, bulletBlaster.getSize().height, null);
        }

        //paint player missiles
        for (BulletMissile bulletMissile : listOfPlayerMissiles)
        {
            g.drawImage(bulletMissile.getImage(), bulletMissile.getLocation().x, bulletMissile.getLocation().y,
                    bulletMissile.getSize().width, bulletMissile.getSize().height, null);
        }

        //paint player lasers
        for (BulletBlaster bulletLaser : listOfPlayerLasers)
        {
            g.drawImage(bulletLaser.getImage(), bulletLaser.getLocation().x, bulletLaser.getLocation().y,
                    bulletLaser.getSize().width, bulletLaser.getSize().height, null);
        }


        //paint enemy shooters
        for (EnemyShooter enemyShooter : listOfEnemyShooters )
        {
            g.drawImage(enemyShooter.getImage(), enemyShooter.getLocation().x, enemyShooter.getLocation().y,
                    enemyShooter.getSize().width, enemyShooter.getSize().height, null);
        }

        //paint enemy kamikazes
        for (EnemyKamikaze enemyKamikaze : listOfEnemyKamikazes)
        {
            g.drawImage(enemyKamikaze.getImage(), enemyKamikaze.getLocation().x, enemyKamikaze.getLocation().y,
                    enemyKamikaze.getSize().width, enemyKamikaze.getSize().height, null);
        }

        //paint enemy splitters
        for (EnemySplitter enemySplitter : listOfEnemySplitters)
        {
            g.drawImage(enemySplitter.getImage(), enemySplitter.getLocation().x, enemySplitter.getLocation().y,
                    enemySplitter.getSize().width, enemySplitter.getSize().height, null);
        }

        //paint enemy bullets
        for (BulletBlaster bullet : listOfEnemyShooterBlasters )
        {
            g.drawImage(bullet.getImage(), bullet.getLocation().x, bullet.getLocation().y,
                    bullet.getSize().width, bullet.getSize().height, null);
        }



        //paint bonuses
        for (Bonus bonus : listOfBonuses )
        {
            g.drawImage(bonus.getImage(), bonus.getLocation().x, bonus.getLocation().y,
                    bonus.getSize().width, bonus.getSize().height, null);
        }


        for (Human human : listOfHumans )
        {
            g.drawImage(human.getHumanImage(), human.getLocation().x, human.getLocation().y,
                    human.getSize().width, human.getSize().height, null);  ////WILL DRAW IMAGE LATER
        }


        //painting boss
        g.drawImage(boss.getImage(), boss.getLocation().x, boss.getLocation().y,
                boss.getSize().width, boss.getSize().height, null);


    }

}
