package appeng.client.render;

import appeng.client.render.model.AutoRotatingBakedModel;
import net.minecraft.client.renderer.model.IBakedModel;

public final class BakedModelUnwrapper {

    private BakedModelUnwrapper() {
    }

    public static <T> T unwrap(IBakedModel model, Class<T> targetClass) {
        if (targetClass.isInstance(model)) {
            return targetClass.cast(model);
        }

        if (model instanceof AutoRotatingBakedModel) {
            model = ((AutoRotatingBakedModel) model).getWrapped();
            if (targetClass.isInstance(model)) {
                return targetClass.cast(model);
            }
        }

        return null;
    }

}
