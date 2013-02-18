/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.map.proxy;

import com.hazelcast.core.EntryListener;
import com.hazelcast.core.EntryView;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.map.MapInterceptor;
import com.hazelcast.map.MapService;
import com.hazelcast.map.SimpleEntryView;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.query.Predicate;
import com.hazelcast.spi.NodeEngine;
import com.hazelcast.util.QueryResultStream;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DataMapProxy extends MapProxySupport implements MapProxy<Data, Data> {

    public DataMapProxy(final String name, final MapService mapService, NodeEngine nodeEngine) {
        super(name, mapService, nodeEngine);
    }

    public Data get(Object k) {
        final NodeEngine nodeEngine = getNodeEngine();
        Data key = nodeEngine.toData(k);
        return getInternal(key);
    }

    public Future<Data> getAsync(final Data key) {
        return getAsyncInternal(key);
    }

    public Data put(final Data k, final Data v) {
        return put(k, v, -1, null);
    }

    public Data put(final Data key, final Data value, final long ttl, final TimeUnit timeunit) {
        return putInternal(key, value, ttl, timeunit);
    }

    public boolean tryPut(final Data key, final Data value, final long timeout, final TimeUnit timeunit) {
        return tryPutInternal(key, value, timeout, timeunit);
    }

    public Data putIfAbsent(final Data k, final Data v) {
        return putIfAbsent(k, v, -1, null);
    }

    public Data putIfAbsent(final Data key, final Data value, final long ttl, final TimeUnit timeunit) {
        return putIfAbsentInternal(key, value, ttl, timeunit);
    }

    public void putTransient(final Data key, final Data value, final long ttl, final TimeUnit timeunit) {
        putTransientInternal(key, value, ttl, timeunit);
    }

    public Future<Data> putAsync(final Data key, final Data value) {
        return putAsyncInternal(key, value);
    }

    public boolean replace(final Data key, final Data oldValue, final Data newValue) {
        return replaceInternal(key, oldValue, newValue);
    }

    public Data replace(final Data key, final Data value) {
        return replaceInternal(key, value);
    }

    public void set(final Data key, final Data value, final long ttl, final TimeUnit timeunit) {
        setInternal(key, value, ttl, timeunit);
    }

    public Data remove(Object k) {
        final NodeEngine nodeEngine = getNodeEngine();
        Data key = nodeEngine.toData(k);
        return removeInternal(key);
    }

    public boolean remove(final Object k, final Object v) {
        final NodeEngine nodeEngine = getNodeEngine();
        Data key = nodeEngine.toData(k);
        Data value = nodeEngine.toData(v);
        return removeInternal(key, value);
    }

    public Data tryRemove(final Data key, final long timeout, final TimeUnit timeunit) throws TimeoutException {
        return tryRemoveInternal(key, timeout, timeunit);
    }

    public Future<Data> removeAsync(final Data key) {
        return removeAsyncInternal(key);
    }

    public boolean containsKey(Object k) {
        final NodeEngine nodeEngine = getNodeEngine();
        Data key = nodeEngine.toData(k);
        return containsKeyInternal(key);
    }

    public boolean containsValue(final Object value) {
        final NodeEngine nodeEngine = getNodeEngine();
        Data v = nodeEngine.toData(value);
        return containsValueInternal(v);
    }

    public Map<Data, Data> getAll(final Set<Data> keys) {
        return getAllDataInternal(keys);
    }

    public void putAll(final Map<? extends Data, ? extends Data> m) {
        putAllDataInternal(m);
    }

    public void clear() {
        clearInternal();
    }

    public void lock(final Data key) {
        lockSupport.lock(getNodeEngine(), key);
    }

    public boolean isLocked(final Data key) {
        return lockSupport.isLocked(getNodeEngine(), key);
    }

    public boolean tryLock(final Data key) {
        return lockSupport.tryLock(getNodeEngine(), key);
    }

    public boolean tryLock(final Data key, final long time, final TimeUnit timeunit) {
        return lockSupport.tryLock(getNodeEngine(), key, time, timeunit);
    }

    public void unlock(final Data key) {
        lockSupport.unlock(getNodeEngine(), key);
    }

    public void forceUnlock(final Data key) {
        lockSupport.forceUnlock(getNodeEngine(), key);
    }

    public Set<Data> keySet() {
        return keySetInternal();
    }

    public Collection<Data> values() {
        return valuesInternal();
    }

    public Set<Entry<Data, Data>> entrySet() {
        return entrySetInternal();
    }

    public void addInterceptor(MapInterceptor interceptor) {
        addMapInterceptorInternal(interceptor);
    }

    public void removeInterceptor(MapInterceptor interceptor) {
        removeMapInterceptorInternal(interceptor);
    }

    public void addEntryListener(final EntryListener<Data, Data> listener, final boolean includeValue) {
        addEntryListenerInternal(listener, null, includeValue);
    }

    public void addEntryListener(EntryListener listener, Predicate predicate, Data key, boolean includeValue) {
        addEntryListenerInternal(listener, predicate, key, includeValue);
    }

    public void removeEntryListener(final EntryListener<Data, Data> listener) {
        removeEntryListenerInternal(listener);
    }

    public void addEntryListener(final EntryListener<Data, Data> listener, final Data key, final boolean includeValue) {
        addEntryListenerInternal(listener, key, includeValue);
    }

    public void removeEntryListener(final EntryListener<Data, Data> listener, final Data key) {
        removeEntryListenerInternal(listener, key);
    }

    @Override
    public EntryView<Data,Data> getEntryView(Data key) {
        return getEntryViewInternal(getNodeEngine().toData(key));
    }

    public boolean evict(final Data key) {
        return evictInternal(key);
    }

    public Set<Data> keySet(final Predicate predicate) {
        return query(predicate, QueryResultStream.IterationType.KEY, true);
    }

    public Set<Entry<Data, Data>> entrySet(final Predicate predicate) {
        return query(predicate, QueryResultStream.IterationType.ENTRY, true);
    }

    public Collection<Data> values(final Predicate predicate) {
        return query(predicate, QueryResultStream.IterationType.VALUE, true);
    }

    public Set<Data> localKeySet() {
        return localKeySetInternal();
    }

    public Set<Data> localKeySet(final Predicate predicate) {
        return localKeySetInternal(predicate);
    }

    public Data executeOnKey(Data key, EntryProcessor entryProcessor) {
        return executeOnKeyInternal(key, entryProcessor);
    }
}
