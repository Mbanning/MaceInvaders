package com.MaceInvaders.game;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

public class GamePlay implements Screen
{
  public float BAD_GUY_WIDTH = 0;
  public float BAD_GUY_HEIGHT = 0;
  public boolean waitOne = false;
  public int dy = 0;
  public int counter = 0;
  public int buttonYUpper = 750;
  public int buttonYlower = 0;
  public int shootArrow = 0;    
  public int deltaX = 0; 
  
  public boolean playing = true;
  public boolean paused = false;
  public boolean inTransition = false;
  public boolean gameOver = false;
  
  public BitmapFont lives;
  public BitmapFont score;
  public BitmapFont wave;
  
  public int numEnemyWaves = 5; 
  public float time = 0.0f;
  
  public int currentWave = 1;
  public long totalScore = 0;
  
  public int shooterRows;
  public int dx = GameData.BAD_GUY_DX;
  public int badGuyRows;
  public int badGuyCols;
  public int badGuySpeed;
  public float arrowSpeed;
  public int mult;
  public int mod;
  public int DX;
  public int DY;
  
  
  public Timer timer;
  MaceInvadersGame game;
  
  
  SpriteBatch batch;
  
  Sprite knight;
  Sprite background;
  Sprite waveBG;
  Sprite waveCompleted;
  Sprite getReady;
  

  
  Hero hero;
  ArrayList<GeneralVillain> baddies = new ArrayList<GeneralVillain>();
  ArrayList<Arrow> arrows = new ArrayList<Arrow>();
  ArrayList<BadArrow> badArrows = new ArrayList<BadArrow>();

  public GamePlay(MaceInvadersGame game)
  {
    this.game = game;
    batch = new SpriteBatch();
    
    setupGameSprites();
    
    BAD_GUY_WIDTH = knight.getWidth();
    BAD_GUY_HEIGHT = knight.getHeight();
    
    hero = new Hero();
    
    lives = new BitmapFont();
    lives.setScale(1.750f);
    lives.setColor(Color.BLACK);
    wave = new BitmapFont();
    wave.setScale(1.750f);
    wave.setColor(Color.BLACK);
    score = new BitmapFont();
    score.setScale(1.750f);
    score.setColor(Color.BLACK);
    timer = new Timer();
    timer.start();
    shooterRows  = 1;
    badGuyRows = 4;
    badGuyCols = 8;
    badGuySpeed = 60;
    arrowSpeed = -5.5f;
    reset();
  }

  public void setupGameSprites()
  {
    
    background = new Sprite(new Texture("map.png"));
    knight = new Sprite(new Texture("knight.png"));
    waveBG = new Sprite(new Texture("background.jpg"));
    waveCompleted = new Sprite(new Texture("wave1.png"));
    
    getReady = new Sprite(new Texture("GetReady.png"));
    
    waveCompleted.setCenter(640, 400);
    getReady.setCenter(640,400);
  }

  public void reset()
  {
    hero.sprite.setX(GameData.HERO_START_X);
    for (int i = 0; i < badGuyCols; ++i)
    {
      for (int j = 0; j < badGuyRows; ++j)
      {
        if( j < shooterRows)
        {
          baddies.add(new ArcherVillain(GameData.BAD_GUY_START_X + i
              * (BAD_GUY_WIDTH + GameData.PADDING), GameData.BAD_GUY_START_Y - j
              * (BAD_GUY_HEIGHT + GameData.PADDING)));
        }
        else{
        baddies.add(new SimpleBadGuy(GameData.BAD_GUY_START_X + i
            * (BAD_GUY_WIDTH + GameData.PADDING), GameData.BAD_GUY_START_Y - j
            * (BAD_GUY_HEIGHT + GameData.PADDING)));
        }
      }
    }
    dx = GameData.BAD_GUY_DX;
    dy = 0;
  }
  
  public void clearDeadVillains()
  {
    int i = 0;
    while (i < baddies.size())
    {
      if (!baddies.get(i).alive)
      {
        baddies.remove(i);
        --i;
      }
      ++i;
    }
  }

  public void clearDeadArrows()
  {
    int i = 0;
    while (i < arrows.size())
    {
      if (!arrows.get(i).alive)
      {
        arrows.remove(i);
        --i;
      }
      ++i;
    }
    int j = 0;
    while(j < badArrows.size())
    {
      if(!badArrows.get(j).alive)
      {
        badArrows.remove(j);
        --j;
      }
      ++j;
    }
  }
  
  @Override
  public void render(float delta)
  {
    batch.begin();
    Gdx.gl.glClearColor(0, 0, 0, 0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    if(playing)
    {
      if(!inTransition)
      {
        updateGame();
      }
      if(inTransition)
      {
        time += delta;
        waveBG.draw(batch);
        if(!arrows.isEmpty())
        {
          arrows.clear();
        }
        if(!badArrows.isEmpty())
        {
          badArrows.clear();
        }
        if(time < 2.0f)
        {
          waveCompleted.draw(batch);
        }
        if(time>=2.0f && time <4.0f)
        {
          getReady.draw(batch);
        }
        if(time > 4.0f)
        {
        reset();
        inTransition = false;
        time = 0.0f;
        }
      }
    }
    batch.end();
  }
  
  public void updateConstants(int wave)
  {
    shooterRows += 1;
    if(wave % 2 == 1)
    {
      badGuyRows++;
    }
    else
    {
      badGuyCols++;
    }
    badGuySpeed -= 5;
    arrowSpeed -= .20f;
    //public int DX;
    //public int DY;
  }

  public void updateBaddies()
  {
    dy = 0;
    if (waitOne)
    {
      for (GeneralVillain b : baddies)
      {
        b.sprite.translateX(dx);
      }
      waitOne = false;
      return;
    }
    waitOne = false;
    for (GeneralVillain v : baddies)
    {
      if(v.canShoot && (((int) (10000*Math.random()) % 37 == 0)))
      {
        badArrows.add(v.fire());
      }
      if ((v.sprite.getX()
          + (BAD_GUY_WIDTH + GameData.PADDING)) >= 1270
          || v.sprite.getX() <= GameData.PADDING )
      {
        waitOne = true;
        dx = -dx;
        dy = GameData.BAD_GUY_DY;
        break;
      }
      if(v.sprite.getY() < hero.sprite.getY())
      {
        game.setNewScreen(2);
      }
    }
    for (GeneralVillain b : baddies)
    {
      if (!waitOne)
      {
        b.sprite.translateX(dx);
      }
      b.sprite.translateY(dy);
    }
  }
  
  public void updateHero()
  {
    if(!hero.alive)
    {
      if(hero.livesLeft > 1)
      {
        hero.alive = true;
        hero.livesLeft--;
        hero.sprite.setCenterX(GameData.HERO_START_X);
      }
      else
      {
         game.setNewScreen(2); 
      }
    }
  }

  public void updateGame()
  {
    if(baddies.isEmpty())
    {
      inTransition = true;
      waveCompleted.setTexture(new Texture("wave" + currentWave + ".png"));
      if(currentWave == 5)
      {
        game.setNewScreen(3);
      }
      if(currentWave < 5)
      {
        currentWave++;
      }
      updateConstants(currentWave);
    }
    counter++;
    shootArrow++;
    clearDeadArrows();
    clearDeadVillains();
    updateHero();

    background.draw(batch);
    lives.draw(batch, "Lives Remaining: " + hero.livesLeft, 150, 35);
    score.draw(batch, "Score: " + totalScore, 150, 725);
    wave.draw(batch, "Current Wave: " + currentWave +"/" + numEnemyWaves, 750 , 725);
    hero.draw(batch);

    for (GeneralVillain b : baddies)
    {
      b.draw(batch);
    }
    
    if (!badArrows.isEmpty())
    {
      updateBadArrows();
    }
    
    if (!arrows.isEmpty())
    {
      updateArrows();
    }
    
    if (counter % badGuySpeed == 0)
    {
      updateBaddies();
      counter = 1;
    }
    if (shootArrow > 30)
    {
      arrows.add(hero.fire());
      shootArrow = 0;
    }
    
    if(Gdx.input.getX() < hero.sprite.getX() &&  Gdx.input.isTouched() && (hero.sprite.getX() > 0))
    {
      hero.update(-3.30f);
    }
    if(Gdx.input.getX() > hero.sprite.getX() && Gdx.input.isTouched() && (hero.sprite.getX() + hero.sprite.getWidth()) < 1280)
    {
      hero.update(3.30f);
    }
  }
  
  public void updateArrows()
  {
    for (Arrow a : arrows)
    {
      a.draw(batch);
      a.update(4.7f);
      if(a.sprite.getY() > 750) a.alive = false;
      for (GeneralVillain v : baddies)
      {
        if (a.sprite.getBoundingRectangle().overlaps(v.sprite.getBoundingRectangle()))
        {
          a.alive = false;
          v.alive = false;
          if(v.canShoot)
          {
            totalScore += 50;
          }
          else if(!v.canShoot)
          {
            totalScore += 25;
          }
        }
      }
    }
  }
  
  public void updateBadArrows()
  {
    for (BadArrow a : badArrows)
    {
      a.draw(batch);
      a.update(arrowSpeed);
      if(a.sprite.getY() < 0) a.alive = false;
      
        if (a.sprite.getBoundingRectangle().overlaps(hero.sprite.getBoundingRectangle()))
        {
          a.alive = false;
          hero.alive = false;
        }
      
    }
  }
  

  @Override
  public void show()
  {
    // TODO Auto-generated method stub
  }

  @Override
  public void resize(int width, int height)
  {
    //game.pause();
    // TODO Auto-generated method stub
  }

  @Override
  public void pause()
  {
    game.pause();
  }

  @Override
  public void resume()
  {
    game.resume();
  }

  @Override
  public void hide()
  {
    dispose();
  }

  @Override
  public void dispose()
  {
   // batch.dispose();
  }
}
