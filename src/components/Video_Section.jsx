import BackButton from "./BackButton";

const videoData = [
    {
      id: 1,
      title: "Introduction to React",
      description: "A brief introduction to the React framework and its core concepts.",
      videoUrl: "https://www.w3schools.com/html/mov_bbb.mp4",
      thumbnail: "https://via.placeholder.com/400x200?text=React+Intro", // Placeholder for thumbnail
      views: "1.2M views",
      time: "2 weeks ago",
    },
    {
      id: 2,
      title: "Understanding JavaScript Promises",
      description: "An in-depth look at promises in JavaScript and how they work.",
      videoUrl: "https://www.w3schools.com/html/movie.mp4",
      thumbnail: "https://via.placeholder.com/400x200?text=JS+Promises", // Placeholder for thumbnail
      views: "860K views",
      time: "1 month ago",
    },
    {
      id: 3,
      title: "Tailwind CSS Tutorial",
      description: "Learn how to style your application using Tailwind CSS.",
      videoUrl: "https://www.w3schools.com/html/mov_bbb.mp4",
      thumbnail: "https://via.placeholder.com/400x200?text=Tailwind+Tutorial", // Placeholder for thumbnail
      views: "1.5M views",
      time: "3 days ago",
    },
  ];
  
  const Video_Section = () => {
    return (
      <section className="bg-gray-900 py-10 px-4">
        <div className="max-w-7xl mx-auto">

  
          {/* Grid Layout for Videos */}
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
            {videoData.map((video) => (
              <div
                key={video.id}
                className="bg-gray-800 rounded-lg overflow-hidden shadow-lg  transform transition-transform lg:hover:scale-105 duration-300"
              >
                {/* Thumbnail or Video Placeholder */}
                <div className="relative">
                  <img
                    src={video.thumbnail}
                    alt={video.title}
                    className="w-full h-48 object-cover"
                  />
                  <span className="absolute bottom-2 right-2 bg-black bg-opacity-75 text-white text-xs px-2 py-1 rounded">
                    {video.time}
                  </span>
                </div>
  
                {/* Video Info */}
                <div className="p-4">
                  <h2 className="text-xl font-semibold text-white mb-1">
                    {video.title}
                  </h2>
                  <p className="text-gray-400 text-sm">{video.description}</p>
  
                  <div className="mt-2 flex items-center justify-between text-gray-500 text-sm">
                    <span>{video.views}</span>
                    <button className="bg-blue-600 text-white px-3 py-1 text-sm rounded-lg hover:bg-blue-500">
                      Play
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>
    );
  };
  
  export default Video_Section;
  