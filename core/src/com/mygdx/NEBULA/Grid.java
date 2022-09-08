package com.mygdx.NEBULA;

import com.badlogic.gdx.assets.AssetManager;

import java.util.List;

public class Grid {
    AssetManager assetManager;
    boolean soundEnabled;
    public Grid(AssetManager assetManager, boolean soundEnabled){
        this.assetManager = assetManager;
        this.soundEnabled = soundEnabled;
    }

    public static void createGrid(Main game, int cols, int rows, float x, float y, float width, float height, List<Cell> cellList){
        int xPlace, yPlace;
        int count = 0;
        for(Cell cell : cellList){
            xPlace = count % cols;
            yPlace = rows - count / rows;

            cell.width = width/cols;
            cell.height = height/rows;
            cell.x = (width/cols) * xPlace + x;
            cell.y = (height/rows) * yPlace + y - cell.height;

            game.batch.draw(cell.texture, cell.x, cell.y, cell.width, cell.height);
            count++;

        }
    }

    public void createGrid(Main game, int cols, int rows, float x, float y, float width, float height, float paddingAll, List<Cell> cellList){
        int xPlace, yPlace;
        int count = 0;
        for(Cell cell : cellList) {
            xPlace = count % cols;

            if (rows == 1)
                yPlace = rows;

            else
                yPlace = rows - count / cols;

            cell.width = width / cols;
            cell.height = height / rows;
            cell.x = (width / cols) * xPlace + x;
            cell.y = (height / rows) * yPlace + y - cell.height;

            if (cell.getCellButton() != null) {

                cell.getCellButton().setHeight(cell.height);
                cell.getCellButton().setWidth(cell.width);
                cell.getCellButton().setX(cell.x);
                cell.getCellButton().setY(cell.y);

                if (cell.getCellButton().getTappedBefore()) {
//                    cell.getCellButton().setTexture(assetManager.get(Assets.start_button_active, Texture.class));
                    System.out.println("test1");
                }
                else {
//                    cell.getCellButton().setTexture(assetManager.get(Assets.start_button_inactive, Texture.class));
                }
                if(cell.getCellButton().getReleased()){
                    if(soundEnabled)
                        game.playSound.play(0.2f);

                }

            }
            game.batch.draw(cell.getTexture(), cell.x + paddingAll / 2, cell.y + paddingAll / 2, cell.width*cell.dimensionRatio - paddingAll, cell.width/cell.dimensionRatio - paddingAll);

//
//                if (cell.getCellButton().getTappedBefore()) {
//                    game.playSound.play(0.2f);
//                    System.out.println("test2");
//                }
//
//                if (cell.getCellButton().getReleased()) {
//                    game.playSound.play(0.2f);
//                    System.out.println("test3");
//
//                }

//            else if(cell.getCellType() == Cell.CELL_TEXTURE)
//                game.batch.draw(cell.texture, cell.x, cell.y, cell.width, cell.height);

                count++;


        }
    }
}
