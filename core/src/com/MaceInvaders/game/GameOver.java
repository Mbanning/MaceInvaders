package com.MaceInvaders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOver implements Screen
{
  SpriteBatch batch;
  Sprite menuBackground;
  Sprite tower;
  Sprite background;
  
  Texture backgroundTexture;
  Texture menuBackgroundTexture;
  Texture towerTexture;
  
  float time = 0.0f;
  
  MaceInvadersGame game;
  
  public GameOver(MaceInvadersGame game)
  {
    this.game = game;
    
    batch = new SpriteBatch();
    
    menuBackgroundTexture = new Texture("GameOver.png");
    towerTexture = new Texture("broken_tower_background.png");
    backgroundTexture = new Texture("background.jpg");
    
    background = new Sprite(backgroundTexture);
    menuBackground = new Sprite(menuBackgroundTexture);
    
    tower = new Sprite(towerTexture);
    menuBackground.setCenter(640, 600);
    tower.setCenter(640, 250);
  }

  @Override
  public void show()
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void render(float delta)
  {
    batch.begin();
    time+=delta;
    Gdx.gl.glClearColor(0, 0, 0, 0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    background.draw(batch);
    menuBackground.draw(batch);
    tower.draw(batch);
    if(time > 2.0f)
    {
      checkMenuTouch();
    }
    batch.end();
    
    
  }

  public void checkMenuTouch()
  {
    if(Gdx.input.isTouched())
    {
       game.setNewScreen(0);
    }
  }
  
  @Override
  public void resize(int width, int height)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void pause()
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void resume()
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void hide()
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void dispose()
  {
    // TODO Auto-generated method stub
    
  }
}
