/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geektcp.common.mosheh.util;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory.Builder;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author geektcp  2021/5/25 11:12
 */
public class BeanMapper {

    private static MapperFacade mapper;

    public BeanMapper() {
    }

    public static <S, D> D map(S source, Class<D> destinationClass) {
        return mapper.map(source, destinationClass);
    }

    public static <S, D> D map(S source, Type<S> sourceType, Type<D> destinationType) {
        return mapper.map(source, sourceType, destinationType);
    }

    public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<S> sourceClass, Class<D> destinationClass) {
        return mapper.mapAsList(sourceList, TypeFactory.valueOf(sourceClass), TypeFactory.valueOf(destinationClass));
    }

    public static <S, D> List<D> mapList(Iterable<S> sourceList, Type<S> sourceType, Type<D> destinationType) {
        return mapper.mapAsList(sourceList, sourceType, destinationType);
    }

    public static <S, D> D[] mapArray(D[] destination, S[] source, Class<D> destinationClass) {
        return mapper.mapAsArray(destination, source, destinationClass);
    }

    public static <S, D> D[] mapArray(D[] destination, S[] source, Type<S> sourceType, Type<D> destinationType) {
        return mapper.mapAsArray(destination, source, sourceType, destinationType);
    }

    public static <E> Type<E> getType(Class<E> rawType) {
        return TypeFactory.valueOf(rawType);
    }

    public static <S, D> void map(S sourceObject, D destinationObject) {
        mapper.map(sourceObject, destinationObject);
    }

    static {
        MapperFactory mapperFactory = ((Builder)(new Builder()).mapNulls(false)).build();
        mapperFactory.getConverterFactory().registerConverter(new BigDecimal2IntConverter());
        mapper = mapperFactory.getMapperFacade();
    }

    static class BigDecimal2IntConverter extends BidirectionalConverter<BigDecimal, Integer> {
        BigDecimal2IntConverter() {
        }

        @Override
        public Integer convertTo(BigDecimal bigDecimal, Type<Integer> type, MappingContext mappingContext) {
            return Integer.valueOf(bigDecimal.intValue());
        }

        @Override
        public BigDecimal convertFrom(Integer integer, Type<BigDecimal> type, MappingContext mappingContext) {
            return new BigDecimal(integer.intValue());
        }
    }
}
