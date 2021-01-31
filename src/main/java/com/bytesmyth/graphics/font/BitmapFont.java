package com.bytesmyth.graphics.font;

import com.bytesmyth.graphics.sprite.SpriteBatcher;
import com.bytesmyth.graphics.mesh.Rectangle;
import com.bytesmyth.graphics.texture.Texture;
import com.bytesmyth.graphics.texture.TextureRegion;
import org.joml.Vector2f;

import java.util.HashMap;
import java.util.Map;

public class BitmapFont {

    private Texture fontTexture;

    private Map<Integer, Glyph> glyphMap = new HashMap<>();

    private float base;
    private float spaceWidth;
    private float lineHeight;

    private float fontScale = 32f;//TODO: read from font file

    public BitmapFont(Texture texture, String descriptor) {
        this.fontTexture = texture;
        initGlyphs(descriptor);
    }

    private void initGlyphs(String fontText) {
        String[] lines = fontText.split("\n");

        String common = lines[1];
        common = common.replace("common ", "");
        Map<String, Integer> commonParams = toParams(common);
        this.base = commonParams.get("base");
        this.lineHeight = commonParams.get("lineHeight");
        this.spaceWidth = lineHeight / 2f;

        for (String line : lines) {
            if (!line.startsWith("char ")){
                continue;
            }

            line = line.replace("char ", "");

            Map<String, Integer> params = toParams(line);

            Glyph glyph = new Glyph();
            glyph.charId = params.get("id");
            float u = params.get("x") / (float) fontTexture.getWidth();
            float v = params.get("y") / (float) fontTexture.getHeight();
            glyph.width = params.get("width");
            glyph.height = params.get("height");
            glyph.region = new TextureRegion(u, v, u + glyph.width / (float) fontTexture.getWidth(), v + glyph.height / (float) fontTexture.getHeight());
            glyph.xOffset = params.get("xoffset");
            glyph.yOffset = params.get("yoffset");
            glyph.xAdvance = params.get("xadvance");

            glyphMap.put(glyph.charId, glyph);
        }
    }

    public Texture getFontTexture() {
        return fontTexture;
    }

    public void drawText(String text, float x, float y, float fontSize, SpriteBatcher batcher) {
        float scaledFontSize = fontSize / fontScale;
        float cursorX = x;
        float cursorY = y;

        for (char c : text.toCharArray()) {
            if (c == ' ') {
                cursorX += spaceWidth * scaledFontSize;
                continue;
            }

            if (c == '\n') {
                cursorX = x;
                cursorY -= lineHeight * scaledFontSize;
                continue;
            }

            Glyph glyph = glyphMap.get((int) c);

            float xOffset = glyph.xOffset * scaledFontSize;
            float yOffset = glyph.yOffset * scaledFontSize;
            float width = glyph.width * scaledFontSize;
            float height = glyph.height * scaledFontSize;
            float cx = width/2f + cursorX + xOffset;
            float cy = -height + cursorY - yOffset;

            batcher.begin(this.getFontTexture());
            batcher.draw(cx, cy, cx + width, cy + height, glyph.region);

            cursorX += glyph.xAdvance * scaledFontSize;
        }
    }

    private Map<String, Integer> toParams(String line) {
        HashMap<String, Integer> map = new HashMap<>();
        String[] params = line.split("\\s+");

        for (String param : params) {
            String[] split = param.split("=");
            map.put(split[0], Integer.parseInt(split[1]));
        }

        return map;
    }

    public Vector2f getTextSize(String text, float fontSize) {
        float scaledFontSize = fontSize / fontScale;

        text = text.trim();

        float maxSizeX = 0;
        float maxSizeY = lineHeight * scaledFontSize;

        float cursorX = 0;

        for (char c : text.toCharArray()) {
            if (c == ' ') {
                cursorX += spaceWidth * scaledFontSize;
                if (cursorX > maxSizeX) maxSizeX = cursorX;
                continue;
            }

            if (c == '\n') {
                cursorX = 0;
                maxSizeY += lineHeight * scaledFontSize;
                continue;
            }

            Glyph glyph = glyphMap.get((int) c);
            cursorX += glyph.xAdvance * scaledFontSize;
            if (cursorX > maxSizeX) maxSizeX = cursorX;
        }

        return new Vector2f(maxSizeX, maxSizeY);
    }

    private static class Glyph {
        int charId;
        float width;
        float height;
        float xOffset;
        float yOffset;
        float xAdvance;
        TextureRegion region;
    }

}
