package com.lumiere.application.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.lumiere.application.services.ImageProcessorService.OptimizedImage;
import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageOptimizer {

    public OptimizedImage optimize(InputStream input, String format) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Thumbnails.of(input)
                .width(1200)
                .outputFormat(format)
                .outputQuality(0.85)
                .toOutputStream(out);

        byte[] bytes = out.toByteArray();
        return new OptimizedImage(new ByteArrayInputStream(bytes), bytes.length, "image/" + format);
    }
}
