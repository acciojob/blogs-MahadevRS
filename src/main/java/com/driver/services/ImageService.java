package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog

        Image image=new Image();
        image.setDescription(description);
        image.setDimensions(dimensions);

        Blog blog=blogRepository2.findById(blogId).get();
        image.setBlog(blog);

        List<Image> imageList=blog.getImageList();
        imageList.add(image);

        blogRepository2.save(blog);

        return image;
    }

    public void deleteImage(Integer id){

        imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        int count=0;

        Image image=imageRepository2.findById(id).get();
        String imagedimension=image.getDimensions();

        String imgxs="",imgys="",screenxs="",screenys="";
        int imgx=0,imgy=0,screenx=0,screeny=0;

        for(int i=0;i<imagedimension.length();i++){
            char c=imagedimension.charAt(i);
            if(c!='X'){
                imgxs+=c;
            }
            else{
                imgys=imagedimension.substring(i+1,imagedimension.length());
                break;
            }
        }

        for(int i=0;i<screenDimensions.length();i++){
            char c=screenDimensions.charAt(i);
            if(c!='X'){
                screenxs+=c;
            }
            else{
                screenys=screenDimensions.substring(i+1,screenDimensions.length());
                break;
            }
        }

        imgx=Integer.parseInt(imgxs);
        imgy=Integer.parseInt(imgys);
        screenx=Integer.parseInt(screenxs);
        screeny=Integer.parseInt(screenys);


        //count=(screenx*screeny)/(imgx*imgy);

        count+=screenx/imgx;
        count+=screeny/imgy;


        return count;
    }
}
