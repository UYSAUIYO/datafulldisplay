package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName air_quality
*/
public class AirQuality implements Serializable {

    /**
    * 
    */
    @ApiModelProperty("")
    private Integer id;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer aqi;
    /**
    * 
    */
    @ApiModelProperty("")
    private Double percentage;
    /**
    * 
    */
    @ApiModelProperty("")
    private Date uploadTime;

    /**
    * 
    */
    private void setId(Integer id){
    this.id = id;
    }

    /**
    * 
    */
    private void setAqi(Integer aqi){
    this.aqi = aqi;
    }

    /**
    * 
    */
    private void setPercentage(Double percentage){
    this.percentage = percentage;
    }

    /**
    * 
    */
    private void setUploadTime(Date uploadTime){
    this.uploadTime = uploadTime;
    }


    /**
    * 
    */
    private Integer getId(){
    return this.id;
    }

    /**
    * 
    */
    private Integer getAqi(){
    return this.aqi;
    }

    /**
    * 
    */
    private Double getPercentage(){
    return this.percentage;
    }

    /**
    * 
    */
    private Date getUploadTime(){
    return this.uploadTime;
    }

}
