package io.github.iwag.newsapp.models;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Convert;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.util.LinkedList;
import java.util.List;

@Root(name = "channel", strict = false)
@Convert(Channel.ChannelConverter.class)
public final class Channel {

    static class ChannelConverter implements Converter<Channel> {
        @Override
        public Channel read(InputNode node) throws Exception {
            Channel channel = new Channel();

            Serializer ser = new Persister();
            InputNode child;

            while( ( child = node.getNext() ) != null ) {
                switch(child.getName()) {
                    case "title":
                        channel.setTitle(child.getValue());
                        break;
                    case "summary":
                        channel.setSummary(child.getValue());
                        break;
                    case "author":
                        channel.setAuthor(child.getValue());
                        break;
                    case "image":
                        if( child.getPrefix() != null && child.getPrefix().equals("itunes") ) {
                            ItunesImage image = new ItunesImage();
                            image.setHref(child.getAttribute("href").getValue());
                            channel.setItunesImage(image);
                        }
                        break;
                    case "item":
                        FeedItem item = ser.read(FeedItem.class, child);
                        if (channel.items == null) {
                            channel.items = new LinkedList<>();
                        }
                        channel.items.add(item);
                        break;
                    default:
                }
            }

            return channel;
        }


        @Override
        public void write(OutputNode node, Channel value) throws Exception {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    @Element(name = "title")
    public String title;

//    @ElementList(name = "description", inline = true, required = false)
//    public List<Description> descriptions;

    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd", prefix = "itunes")
    @Element(name = "summary", required = false)
    public String summary;

    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @Element(name = "author", required = false)
    public String author;

    @ElementList(name = "item", inline = true)
    public List<FeedItem> items;

    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd", prefix = "itunes")
    @Element(name = "image", required = false)
    public ItunesImage itunesImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<FeedItem> getItems() {
        return items;
    }

    public void setItems(List<FeedItem> items) {
        this.items = items;
    }

    public ItunesImage getItunesImage() {
        return itunesImage;
    }

    public void setItunesImage(ItunesImage itunesImage) {
        this.itunesImage = itunesImage;
    }
}
