package com.vivo.bigdata.integration.jdbc.mapper;

import org.apache.storm.jdbc.common.Column;
import org.apache.storm.tuple.ITuple;

import java.io.Serializable;
import java.util.List;

public interface JdbcMapper  extends Serializable {
    List<Column> getColumns(ITuple tuple);
}