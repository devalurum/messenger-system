package org.urumov.messengersystem.service;

import org.springframework.transaction.annotation.Transactional;
import org.urumov.messengersystem.dto.ItemFeedDto;
import org.urumov.messengersystem.entities.ItemFeed;
import org.urumov.messengersystem.entities.NewsFeed;

import java.util.List;

public interface NewsFeedService {

    NewsFeed createNewsFeed();

    ItemFeed createItem(long id, ItemFeedDto itemFeedDto);

    boolean deleteItem(long newsFeedId, long itemFeedId);

    boolean deleteAllItems(long newsFeedId);

    List<ItemFeedDto> getItemsFeed(long id);

    ItemFeedDto getItemFeed(long newsFeedId, long itemFeedId);
}
