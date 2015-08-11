
package org.gearvrf.keyboard.model;

import java.util.ArrayList;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.keyboard.R;
import org.gearvrf.keyboard.util.SceneObjectNames;

import android.content.res.Resources;
import android.content.res.TypedArray;

public class SphereStaticList {

    public ArrayList<GVRSceneObject> listFlag;
    public static int MOVEABLE = 0;
    public static int ANSWERING = 1;
    public static int RESTORING = 2;

    public SphereStaticList(GVRContext gvrContext) {
        getSpheres(gvrContext, R.array.spheres);
    }

    public void changeLockStateAllSpheresEyePointee(final boolean lock) {
        for (GVRSceneObject sphereFlag : listFlag) {
            if (sphereFlag != null && sphereFlag.getParent() != null
                    && sphereFlag.getEyePointeeHolder() != null) {
                sphereFlag.getEyePointeeHolder().setEnable(lock);
            }
        }
    }

    private void getSpheres(GVRContext gvrContext, int array) {
        listFlag = new ArrayList<GVRSceneObject>();
        Resources res = gvrContext.getContext().getResources();
        TypedArray spheres = res.obtainTypedArray(array);

        for (int i = 0; i < spheres.length(); i++) {
            int type = spheres.getResourceId(i, -1);
            TypedArray sphere = res.obtainTypedArray(type);
            SphereFlag objectSphere = new SphereFlag(gvrContext, sphere);
            Vector3D parentPosition = objectSphere.getInitialPositionVector();

            GVRSceneObject parent = new GVRSceneObject(gvrContext, new GVRAndroidResource(
                    gvrContext, R.drawable.hit_area_half), new GVRAndroidResource(gvrContext,
                    R.raw.empty));
            parent.setName(SceneObjectNames.SPHERE_FLAG_PARENT);
            parent.getTransform().setPosition((float) parentPosition.getX(),
                    (float) parentPosition.getY(), (float) parentPosition.getZ());
            parent.addChildObject(objectSphere);
            listFlag.add(parent);
        }
    }

}