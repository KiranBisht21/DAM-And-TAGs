package com.ttn.testproject.core.models;

import com.adobe.cq.sightly.WCMUsePojo;
import com.adobe.granite.asset.api.Asset;
import com.adobe.granite.asset.api.AssetManager;
import com.adobe.granite.asset.api.Rendition;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyAsset extends WCMUsePojo {
    List<String> myRenditions;
    boolean specificrendition;
    String specificResolution;


    @Override
    public void activate() throws Exception {
        ValueMap properties=getValueMapForRenditionComponent();
        String path=properties.get("assetpath",String.class);
        ResourceResolver resourceResolver=getResourceResolver();
        AssetManager assetManager=resourceResolver.adaptTo(AssetManager.class);
        Asset asset =assetManager.getAsset(path);

        Iterator<Rendition> renditions=(Iterator<Rendition>) (asset.listRenditions());
        myRenditions=new ArrayList<String>();
        int i =0;
        for(;renditions.hasNext(); ++i)
        {
            Rendition rendition=renditions.next();
            String name=rendition.getPath();
            myRenditions.add(name);
        }
        String renderedImage="";
        String imgResolution=properties.get("resolution" ,String.class);

        for(int j=0;j<myRenditions.size();j++)
        {
            String mypath=myRenditions.get(j);
            if(mypath.contains("original"))
            {
                renderedImage=mypath;
            }
            specificrendition=mypath.contains(imgResolution);
            if(specificrendition==true)
            {
                specificResolution=mypath;
                break;
            }
        }
        if(specificrendition==false)
        {
            specificResolution=renderedImage;
        }


    }

    private ValueMap getValueMapForRenditionComponent() {
        String speceficpath="/content/testproject/us/en/jcr:content/root/container/container/assetcomponent";
        Resource myresource=getResourceResolver().getResource(speceficpath);
        ValueMap valueMap=getResource().adaptTo(ValueMap.class);
        return valueMap;

    }

    public List<String> getRenditionList()
    {
        return myRenditions;
    }
    public String getSpeceficRendition()
    {
        return specificResolution;
    }




}
