import { useState, useEffect } from 'react';

const useNewsApi = () => {
    const [news, setNews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/news?numberOfNews=3');
                if (!response.ok) {
                    console.error('Failed to fetch data');
                }

                const data = await response.json();
                setNews(data);
                setLoading(false);
            } catch (error) {
                setError(error.message);
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    return { news, loading, error };
};

export default useNewsApi;
