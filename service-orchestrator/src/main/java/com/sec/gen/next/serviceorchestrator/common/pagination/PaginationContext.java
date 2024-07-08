package com.sec.gen.next.serviceorchestrator.common.pagination;

public class PaginationContext {
    private static final ThreadLocal<Pagination> paginationThread = new ThreadLocal<>();

    public static Pagination getPagination() {
        return paginationThread.get();
    }

    public static void setPagination(Pagination pagination) {
        paginationThread.set(pagination);
    }

    public static void clear() {
        paginationThread.remove();
    }
}
