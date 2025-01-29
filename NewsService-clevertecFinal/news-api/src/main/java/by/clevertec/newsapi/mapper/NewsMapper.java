package by.clevertec.newsapi.mapper;

import by.clevertec.newsapi.adapter.input.web.dto.request.news.NewsNewRequestDto;
import by.clevertec.newsapi.adapter.input.web.dto.request.news.NewsUpdateRequestDto;
import by.clevertec.newsapi.adapter.input.web.dto.response.news.NewsDto;
import by.clevertec.newsapi.adapter.input.web.dto.response.news.NewsDtoWithComments;
import by.clevertec.newsapi.adapter.output.jpa.entity.NewsEntity;
import by.clevertec.newscore.domain.news.CreatedNews;
import by.clevertec.newscore.domain.news.News;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsMapper {

    CreatedNews toNewDomain(NewsNewRequestDto dto);

    CreatedNews toUpdatedDomain(NewsUpdateRequestDto dto);

    NewsDto toDto(News domain);

    NewsEntity toEntity(News domain);

    News toDomain(NewsEntity entity);

    List<News> toDomain(List<NewsEntity> entity);

    List<NewsDto> toDto(List<News> domain);

    NewsDtoWithComments toDtoWithComments(News domain);

}
