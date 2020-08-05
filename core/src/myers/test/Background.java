package myers.test;

import static myers.test.MayhemGame.textureAtlas;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background {

    private TextureRegion[] backgrounds;
    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollingSpeed;

    private SpriteBatch batch;

    public Background(SpriteBatch batch){
        this.batch = batch;

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("tex_Water");
        backgrounds[1] = textureAtlas.findRegion("water2");
        backgrounds[2] = textureAtlas.findRegion("water3");
        backgrounds[3] = textureAtlas.findRegion("water4");
        backgroundMaxScrollingSpeed = (float) MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE / 4;
    }

    public void render(float deltaTime){
        batch.begin();

        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        for(int layer = 0; layer < backgroundOffsets.length; layer++){
            if(backgroundOffsets[layer] > MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE){
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer],0,-backgroundOffsets[layer], MayhemGame.VIRTUAL_WIDTH*MayhemGame.SCALE,MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE);
            batch.draw(backgrounds[layer],0,-backgroundOffsets[layer]+ MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE,MayhemGame.VIRTUAL_WIDTH*MayhemGame.SCALE,MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE);
        }

        batch.end();
    }
}
