package com.MaceInvaders.game;

import com.badlogic.gdx.Game;

public class MaceInvadersGame extends Game 
{
  public MaceInvadersGame()
  {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void create()
  {
    setScreen(new Menu(this));
  }
  
  public void setNewScreen(int num)
  {
    switch(num){
      case 0: setScreen(new Menu(this));
              break;
      case 1: setScreen(new GamePlay(this));
              break;
      case 2: setScreen(new GameOver(this));
              break;
      case 3: setScreen(new GameWon(this));
              break;
    }
  }
}
