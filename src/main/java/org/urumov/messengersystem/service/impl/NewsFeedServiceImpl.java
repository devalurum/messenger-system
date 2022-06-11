package org.urumov.messengersystem.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.urumov.messengersystem.domain.dto.ItemFeedDto;
import org.urumov.messengersystem.domain.mapper.ItemFeedMapper;
import org.urumov.messengersystem.domain.entity.Department;
import org.urumov.messengersystem.domain.entity.ItemFeed;
import org.urumov.messengersystem.domain.entity.NewsFeed;
import org.urumov.messengersystem.domain.entity.User;
import org.urumov.messengersystem.repository.DepartmentRepository;
import org.urumov.messengersystem.repository.ItemFeedRepository;
import org.urumov.messengersystem.repository.NewsFeedRepository;
import org.urumov.messengersystem.repository.UserRepository;
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
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

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
    public ItemFeed createItem(long id, @NonNull ItemFeedDto itemFeedDto, User creator) {
        return newsFeedRepository.findById(id)
                .map(newsFeed -> {
                    long departmentId = itemFeedDto.getDepartmentId();
                    ItemFeed itemFeed = itemFeedMapper.toModel(itemFeedDto);
                    Department department = departmentRepository.findById(departmentId)
                            .orElseThrow(EntityNotFoundException::new);
                    itemFeed.setDepartment(department);
                    itemFeed.setSender(creator);
                    itemFeed.setNewsFeed(newsFeed);
                    newsFeed.getFeeds().add(itemFeed);
                    ItemFeed item = itemFeedRepository.save(itemFeed);
                    department.getItemFeeds().add(item);
                    newsFeedRepository.save(newsFeed);
                    departmentRepository.save(department);
                    return itemFeed;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public boolean deleteItem(long newsFeedId, long itemFeedId) {
        return newsFeedRepository.findById(newsFeedId).map(
                newsFeed -> {
                    itemFeedRepository.findById(itemFeedId).map(
                            itemFeed -> {
                                newsFeed.getFeeds().remove(itemFeed);
                                itemFeedRepository.delete(itemFeed);
                                return newsFeedRepository.save(newsFeed);
                            });
                    return true;
                }).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public boolean deleteAllItems(long newsFeedId) {
        return newsFeedRepository.findById(newsFeedId)
                .map(newsFeed -> {
                    newsFeed.getFeeds().forEach(item -> {
                        User user = item.getSender();
                        user.getItemFeeds().remove(item);
                        userRepository.save(user);
                        item.setSender(null);
                        itemFeedRepository.delete(item);
                    });
                    newsFeed.setFeeds(null);
                    newsFeedRepository.save(newsFeed);
                    return true;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public boolean deleteAllItemsFromDepartment(long newsFeedId, long departmentId) {
        return newsFeedRepository.findById(newsFeedId)
                .map(newsFeed -> {
                    Department department = departmentRepository.findById(departmentId)
                            .orElseThrow(EntityNotFoundException::new);
                    itemFeedRepository.deleteAll(department.getItemFeeds());
                    newsFeed.getFeeds().removeAll(department.getItemFeeds());
                    department.setItemFeeds(null);
                    departmentRepository.save(department);
                    newsFeedRepository.save(newsFeed);
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
    public List<ItemFeedDto> getItemsFeedFromDepartment(long newsId, long departmentId) {
        List<ItemFeed> itemFeedList = itemFeedRepository.findAllByNewsFeed_IdAndDepartment_Id(newsId, departmentId);
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
