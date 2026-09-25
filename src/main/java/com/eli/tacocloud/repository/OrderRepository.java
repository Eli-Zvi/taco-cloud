package com.eli.tacocloud.repository;

import com.eli.tacocloud.model.TacoOrder;

public interface OrderRepository {

    TacoOrder save(TacoOrder order);
}
