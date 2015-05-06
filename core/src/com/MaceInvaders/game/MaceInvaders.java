package com.MaceInvaders.game;

import java.util.ArrayList;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MaceInvaders extends ApplicationAdapter
{
  public float BAD_GUY_WIDTH = 0;
  public float BAD_GUY_HEIGHT = 0;
  public boolean waitOne = false;
  public int dx = GameData.BAD_GUY_DX;
  public int dy = 0;
  public int counter = 0;
  public int buttonYUpper = 750;
  public int buttonYlower = 0;
  public int shootArrow = 0;    
  public int deltaX = 0; 
  
  public boolean playing = false;
  public boolean paused = false;
  public boolean inTransition = false;
  public boolean gameOver = false;
  //public Label score;
  //public Skin skin; 
  public BitmapFont lives;
  
  
  
  SpriteBatch batch;
  Sprite menuBackground;
  Sprite menuButton;
  Sprite knight;
  Sprite background;
  Sprite left;
  Sprite right;
  Sprite shoot;
  
  Texture menuBackgroundTexture;
  Texture menuButtonTexture;
  Texture leftTexture;
  Texture rightTexture;
  Texture shootTexture;
  Texture backgroundTexture;
  Texture knightTexture;
  
  Hero hero;
  ArrayList<GeneralVillain> baddies = new ArrayList<GeneralVillain>();
  ArrayList<Arrow> arrows = new ArrayList<Arrow>();
  ArrayList<BadArrow> badArrows = new ArrayList<BadArrow>();

  @Override
  public void resize(int width, int height)
  {
    // TODO Auto-generated method stub
    super.resize(width, height);
  }

  @Override
  public void pause()
  {
    paused = true;
    super.pause();
  }

  @Override
  public void resume()
  {
    paused = false;
    super.resume();
  }

  @Override
  public void dispose()
  {
    // TODO Auto-generated method stub
    super.dispose();
  }
  
  public void setupMenu()
  {
    menuBackgroundTexture = new Texture("MenuText.png");
    menuButtonTexture = new Texture("NewGame.png");
    
    menuBackground = new Sprite(menuBackgroundTexture);
    menuButton = new Sprite(menuButtonTexture);
    
    menuBackground.setCenter(640, 650);
    menuButton.setCenter(640, 350);
  }
  
  public void setupGameSprites()
  {
    knightTexture = new Texture("knight.png");
    backgroundTexture = new Texture("map.png");

    background = new Sprite(backgroundTexture);
    knight = new Sprite(knightTexture);

    
    rightTexture = new Texture("right.png");
    leftTexture = new Texture("left.png");
    shootTexture = new Texture("shoot.png");

    shoot = new Sprite(shootTexture);
    right = new Sprite(rightTexture);
    left = new Sprite(leftTexture);

    right.setCenter(GameData.LEFT_CENTER_X + left.getWidth() / 2 + right.getWidth() / 2 + 40,
        GameData.LEFT_CENTER_Y);
    left.setCenter(GameData.LEFT_CENTER_X, GameData.LEFT_CENTER_Y);
    shoot.setCenter(GameData.SHOOT_CENTER_X, GameData.SHOOT_CENTER_Y);
  }

  @Override
  public void create()
  {
    batch = new SpriteBatch();
    
    setupMenu();
    setupGameSprites();
    
    BAD_GUY_WIDTH = knight.getWidth();
    BAD_GUY_HEIGHT = knight.getHeight();
    
    hero = new Hero();
    lives = new BitmapFont();
    lives.setScale(2.50f);
    reset();
  }

  public void reset()
  {
    hero.sprite.setX(GameData.HERO_START_X);
    for (int i = 0; i < GameData.BAD_GUY_COLS; ++i)
    {
      for (int j = 0; j < GameData.BAD_GUY_ROWS; ++j)
      {
        if( j < 2)
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
  
  public void showMenu()
  {
    menuBackground.draw(batch);
    menuButton.draw(batch);
  }
  @Override
  public void render()
  {
    Gdx.gl.glClearColor(0, 0, 0, 0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    if(!playing)
    {
      showMenu();
      checkMenuTouch();
    }
    else if(!playing)
    {
      
    }
    if(playing)
    {
      if(!inTransition)
      {
        updateGame();
      }
      if(inTransition)
      {
        reset();
        inTransition = false;
      }
    }
    batch.end();
  }
  
  public void checkMenuTouch()
  {
    if(menuButton.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.input.getY()))
    {
      playing = true;
    }
  }

  public boolean isWithinBounds(float x, float y, Sprite sprite)
  {
    if (Gdx.input.isTouched() && (x > sprite.getX()) && (x < sprite.getX() + sprite.getWidth())
        && (y > (750 - right.getY() - right.getHeight()) - 20) && (y < (750 - right.getY())))
      return true;
    return false;
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
      if(v.canShoot && (((int) (10000*Math.random()) % 57 == 0)))
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
        pause();
        dispose();
        gameOver = true;
        playing = false;
        
      }
    }
  }

  public void updateGame()
  {
    if(baddies.isEmpty())
    {
      inTransition = true;
    }
    counter++;
    shootArrow++;
    clearDeadArrows();
    clearDeadVillains();
    updateHero();
    background.draw(batch);
    lives.draw(batch, "Lives Remaining: " + hero.livesLeft, 150, 725);
    
//    left.draw(batch);
//    right.draw(batch);
//    shoot.draw(batch);
    hero.draw(batch);
    for (GeneralVillain b : baddies)
    {
      b.draw(batch);
    }
    if (!badArrows.isEmpty())
    {
      for (BadArrow a : badArrows)
      {
        a.draw(batch);
        a.update(-4.7f);
        if(a.sprite.getY() < 0) a.alive = false;
        
          if (a.sprite.getBoundingRectangle().overlaps(hero.sprite.getBoundingRectangle()))
          {
            a.alive = false;
            hero.alive = false;
          }
        
      }
    }
    if (!arrows.isEmpty())
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
          }
        }
      }
    }
    if (counter % 50 == 0)
    {
      updateBaddies();
      counter = 1;
    }
    //isWithinBounds(Gdx.input.getX(), Gdx.input.getY(), shoot) && 
    if (shootArrow > 30)
    {
      arrows.add(hero.fire());
      shootArrow = 0;
    }
    
    if(Gdx.input.getX() < 640 &&  Gdx.input.isTouched() && (hero.sprite.getX() > 0))
    {
      hero.update(-3.30f);
    }
    if(Gdx.input.getX() > 640 && Gdx.input.isTouched() && (hero.sprite.getX() + hero.sprite.getWidth()) < 1280)
    {
      hero.update(3.30f);
    }

    //if (isWithinBounds(Gdx.input.getX(), Gdx.input.getY(), right)
    //    && (hero.sprite.getX() + hero.sprite.getWidth()) < 1280)
   // {
   //   hero.update(2.30f);
   // }
    //if (isWithinBounds(Gdx.input.getX(), Gdx.input.getY(), left) && (hero.sprite.getX() > 0))
    //{
    //  hero.update(-2.30f);
    //}
  }
}
