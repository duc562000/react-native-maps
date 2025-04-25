package com.rnmaps.maps;
 
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.UIManagerModule;
 
import java.util.function.Function;
 
public class MapUIBlock implements UIBlock {
    private int tag;
    private Promise promise;
    private ReactApplicationContext context;
    private Function<MapView, Void> mapOperation;
 
    public MapUIBlock(int tag, Promise promise, ReactApplicationContext context, Function<MapView, Void> mapOperation) {
        this.tag = tag;
        this.promise = promise;
        this.context = context;
        this.mapOperation = mapOperation;
    }
 
    @Override
    public void execute(NativeViewHierarchyManager nvhm) {
        MapView view = (MapView) nvhm.resolveView(tag);
        if (view == null) {
            promise.reject("AirMapView not found");
            return;
        }
        if (view.map == null) {
            promise.reject("AirMapView.map is not valid");
            return;
        }
 
        mapOperation.apply(view);
    }
 
    public void addToUIManager() {
        UIManagerModule uiManager = context.getNativeModule(UIManagerModule.class);
        uiManager.addUIBlock(this);
    }
}