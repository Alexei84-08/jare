/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2020 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge,  to any person obtaining
 * a copy  of  this  software  and  associated  documentation files  (the
 * "Software"),  to deal in the Software  without restriction,  including
 * without limitation the rights to use,  copy,  modify,  merge, publish,
 * distribute,  sublicense,  and/or sell  copies of the Software,  and to
 * permit persons to whom the Software is furnished to do so,  subject to
 * the  following  conditions:   the  above  copyright  notice  and  this
 * permission notice  shall  be  included  in  all copies or  substantial
 * portions of the Software.  The software is provided  "as is",  without
 * warranty of any kind, express or implied, including but not limited to
 * the warranties  of merchantability,  fitness for  a particular purpose
 * and non-infringement.  In  no  event shall  the  authors  or copyright
 * holders be liable for any claim,  damages or other liability,  whether
 * in an action of contract,  tort or otherwise,  arising from, out of or
 * in connection with the software or  the  use  or other dealings in the
 * software.
 */
package io.jare.dynamo;

import com.jcabi.dynamo.Attributes;
import com.jcabi.dynamo.Item;
import io.jare.model.Domain;
import io.jare.model.Usage;
import java.io.IOException;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Dynamo domain.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle MultipleStringLiteralsCheck (500 lines)
 */
@ToString
@EqualsAndHashCode(of = "item")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class DyDomain implements Domain {

    /**
     * The item.
     */
    private final transient Item item;

    /**
     * Ctor.
     * @param itm Item
     */
    public DyDomain(final Item itm) {
        this.item = itm;
    }

    @Override
    public String owner() throws IOException {
        return this.item.get("user").getS();
    }

    @Override
    public String name() throws IOException {
        return this.item.get("domain").getS();
    }

    @Override
    public void delete() throws IOException {
        this.item.frame().table().delete(
            new Attributes().with("domain", this.name())
        );
    }

    @Override
    public Usage usage() {
        return new DyUsage(this.item);
    }

}
