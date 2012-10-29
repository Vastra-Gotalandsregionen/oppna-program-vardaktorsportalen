package se.vgregion.portal.vap.domain.searchresult;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Domain class representing a document.
 *
 * @author Patrik Bergström
 */
@JsonIgnoreProperties({"revisiondateAsDateObject", "dcDateValidToAsDateObject"})
public class Document {

    private String id;
    private String id_hash;
    private String title;
    private String revisiondate;
    private String source;
    private String url;
    private String description;
    private String author;
    private String extracted_html;
    private String format;
    private List<String> dc_publisher_forunit;
    private String dc_date_validto_dt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_hash() {
        return id_hash;
    }

    public void setId_hash(String id_hash) {
        this.id_hash = id_hash;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRevisiondate() {
        return revisiondate;
    }

    public void setRevisiondate(String revisiondate) {
        this.revisiondate = revisiondate;
    }

    /**
     * Get the revisiondate as a {@link Date} object instead of as a {@link String}.
     *
     * @return the {@link Date}
     */
    public Date getRevisiondateAsDateObject() {
        if (revisiondate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(revisiondate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String sourceUrl) {
        this.url = sourceUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getExtracted_html() {
        return extracted_html;
    }

    public void setExtracted_html(String extracted_html) {
        this.extracted_html = extracted_html;
    }

    public String getFormat() {
        return format;
    }

    /**
     * Setter method that takes a list argument and takes the first element in the list as the format string. Used by
     * JSON parser.
     *
     * @param formatList
     */
    public void setFormat(List<String> formatList) {

        // Temporary setter while repository returns format as a list

        String formatToSet = "";

        if (formatList != null) {
            if (formatList.size() > 0) {
                formatToSet = formatList.get(0);
            }
        }

        this.format = formatToSet;
    }

    public List<String> getDc_publisher_forunit() {
        return dc_publisher_forunit;
    }

    public void setDc_publisher_forunit(List<String> dc_publisher_forunit) {
        this.dc_publisher_forunit = dc_publisher_forunit;
    }

    @Override
    public String toString() {
        return "Document{"
                + "id='" + id + '\''
                + ", title='" + title + '\''
                + '}';
    }

    public String getDc_date_validto_dt() {
        return dc_date_validto_dt;
    }

    public void setDc_date_validto_dt(String dc_date_validto_dt) {
        this.dc_date_validto_dt = dc_date_validto_dt;
    }

    /**
     * Get the "valid to" date as a {@link Date} object.
     *
     * @return the "valid to" date as a {@link Date} object.
     */
    public Date getDcDateValidToAsDateObject() {
        Date dcDateValidToAsDateObject = null;

        if (dc_date_validto_dt != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            try {
                dcDateValidToAsDateObject = sdf.parse(dc_date_validto_dt);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        return dcDateValidToAsDateObject;

    }


}
