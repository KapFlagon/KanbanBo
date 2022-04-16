package utils.images;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGOMSVGElement;
import org.apache.batik.bridge.*;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class BufferedImageSVGTranscoder extends ImageTranscoder {

    private BufferedImage img = null;

    public BufferedImage getBufferedImage() {
        return img;
    }

    @Override
    public BufferedImage createImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void writeImage(BufferedImage img, TranscoderOutput to) throws TranscoderException {
        this.img = img;
    }

    public ImageView parseSvgFileWithNewColours(ImageView imageView, URL svgFileUrl, Map<String, String> colours) {
        return parseSvgFileWithNewColours(imageView, svgFileUrl, null, colours);
    }

    public ImageView parseSvg(ImageView imageView, InputStream svgInputStream) {
        TranscoderInput transIn = new TranscoderInput(svgInputStream);
        try {
            transcode(transIn, null);
            Image image = SwingFXUtils.toFXImage(getBufferedImage(), null);
            imageView.setImage(image);
        } catch (TranscoderException exception) {
            exception.printStackTrace();
        }
        return imageView;
    }

    public ImageView parseSvgFileWithNewColours(ImageView imageView, URL svgFileUrl, InputStream svgInputStream, Map<String, String> colours) {
        try {
            Document document = getDocumentPathNodes(svgFileUrl, svgInputStream);
            NodeList nodeList = getNodeList(document);

            updateColoursInNodes(nodeList, colours);

            try {
                pushUpdateToImageView(imageView, document);
            } catch (TranscoderException exception) {
                exception.printStackTrace();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return imageView;
    }

    private Document getDocumentPathNodes(URL svgFileUrl, InputStream svgFileInputStream) throws URISyntaxException, IOException {
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory documentFactory = new SAXSVGDocumentFactory(parser);
        URI uri = svgFileUrl.toURI();
        Document document;
        if(svgFileInputStream != null) {
            document = documentFactory.createDocument(uri.toString(), svgFileInputStream);
        } else {
            document = documentFactory.createDocument(uri.toString());
        }

        UserAgent userAgent = new UserAgentAdapter();
        DocumentLoader loader = new DocumentLoader(userAgent);
        BridgeContext ctx = new BridgeContext(userAgent, loader);
        ctx.setDynamicState(BridgeContext.DYNAMIC);
        GVTBuilder builder = new GVTBuilder();
        GraphicsNode rootGN = builder.build(ctx, document);

        return document;
    }

    private NodeList getNodeList(Document document) {
        SVGOMSVGElement myRootSVGElement = (SVGOMSVGElement) document.getDocumentElement();
        return myRootSVGElement.getElementsByTagName("path");
    }

    private void updateColoursInNodes(NodeList nodeList, Map<String, String> colours) {
        for(int iterator=0; iterator < nodeList.getLength(); ++iterator){
            Element elt = (Element)nodeList.item(iterator);
            String originalFillValue = elt.getAttribute("fill");
            if(Color.web(originalFillValue).equals(Color.web(colours.get("baseColour")))) {
                elt.setAttribute("fill", colours.get("newColour"));
            }
        }
    }

    private void pushUpdateToImageView(ImageView imageView, Document document) throws TranscoderException {
        TranscoderInput transIn = new TranscoderInput(document);
        transcode(transIn, null);
        Image image = SwingFXUtils.toFXImage(getBufferedImage(), null);
        imageView.setImage(image);
    }
}
