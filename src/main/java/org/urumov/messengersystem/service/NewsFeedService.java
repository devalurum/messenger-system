package org.urumov.messengersystem.service;

import org.urumov.messengersystem.domain.dto.ItemFeedDto;
import org.urumov.messengersystem.domain.entity.ItemFeed;
import org.urumov.messengersystem.domain.entity.NewsFeed;
import org.urumov.messengersystem.domain.entity.User;

import java.util.List;

public interface NewsFeedService {

    NewsFeed createNewsFeed();

    ItemFeed createItem(long id, ItemFeedDto itemFeedDto, User creator);

    boolean deleteItem(long newsFeedId, long itemFeedId);

    boolean deleteAllItems(long newsFeedId);

    boolean deleteAllItemsFromDepartment(long newsFeedId, long departmentId);

    List<ItemFeedDto> getItemsFeed(long id);

    List<ItemFeedDto> getItemsFeedFromDepartment(long newsId, long departmentId);

    ItemFeedDto getItemFeed(long newsFeedId, long itemFeedId);
}
