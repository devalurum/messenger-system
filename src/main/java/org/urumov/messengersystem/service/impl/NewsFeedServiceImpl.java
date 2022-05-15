package org.urumov.messengersystem.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.urumov.messengersystem.domain.dto.ItemFeedDto;
import org.urumov.messengersystem.domain.model.ItemFeed;
import org.urumov.messengersystem.domain.model.NewsFeed;
import org.urumov.messengersystem.domain.mapper.ItemFeedMapper;
import org.urumov.messengersystem.domain.mapper.NewsFeedMapper;
import org.urumov.messengersystem.repository.ItemFeedRepository;
import org.urumov.messengersystem.repository.NewsFeedRepository;
import org.urumov.messengersystem.service.NewsFeedService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsFeedServiceImpl implements NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;
    private final ItemFeedRepository itemFeedRepository;
    private final ItemFeedMapper itemFeedMapper;

    @Override
    @Transactional
    public NewsFeed createNewsFeed() {
        if (newsFeedRepository.findById(1L).isPresent())
            throw new IllegalArgumentException("NewsFeed already created.");

        NewsFeed newsFeed = NewsFeed.builder()
                .id(1L)
                .build();


        return newsFeedRepository.save(newsFeed);
    }

    @Override
    @Transactional
    public ItemFeed createItem(long id, @NonNull ItemFeedDto itemFeedDto) {
        return newsFeedRepository.findById(id)
                .map(newsFeed -> {
                    ItemFeed itemFeed = itemFeedMapper.toModel(itemFeedDto);
                    newsFeed.getFeeds().add(itemFeed);
                    newsFeedRepository.save(newsFeed);
                    return itemFeed;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public boolean deleteItem(long newsFeedId, long itemFeedId) {
        return itemFeedRepository.findByIdAndNewsFeed_Id(itemFeedId, newsFeedId)
                .map(itemFeed -> {
                    itemFeedRepository.deleteItemFeedByIdAndNewsFeed_Id(itemFeed.getId(), newsFeedId);
                    return true;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public boolean deleteAllItems(long newsFeedId) {
        return newsFeedRepository.findById(newsFeedId)
                .map(newsFeed -> {
                    itemFeedRepository.findAllByNewsFeed_Id(newsFeed.getId());
                    return true;
                })
                .orElseThrow(EntityNotFoundException::new);
    }


    @Override
    @Transactional
    public List<ItemFeedDto> getItemsFeed(long id) {
        List<ItemFeed> itemFeedList = itemFeedRepository.findAllByNewsFeed_Id(id);
        return itemFeedList.stream()
                .map(itemFeedMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemFeedDto getItemFeed(long newsFeedId, long itemFeedId) {
        return itemFeedRepository.findByIdAndNewsFeed_Id(itemFeedId, newsFeedId)
                .map(itemFeedMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }
}
