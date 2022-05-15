package org.urumov.messengersystem.service;

import org.urumov.messengersystem.domain.dto.ItemFeedDto;
import org.urumov.messengersystem.domain.model.ItemFeed;
import org.urumov.messengersystem.domain.model.NewsFeed;

import java.util.List;

public interface NewsFeedService {

    NewsFeed createNewsFeed();

    ItemFeed createItem(long id, ItemFeedDto itemFeedDto);

    boolean deleteItem(long newsFeedId, long itemFeedId);

    boolean deleteAllItems(long newsFeedId);

    List<ItemFeedDto> getItemsFeed(long id);

    ItemFeedDto getItemFeed(long newsFeedId, long itemFeedId);
}
