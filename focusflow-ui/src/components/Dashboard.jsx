function Dashboard({ stats, weekly }) {
  if (!stats) return <p>Loading...</p>;

  const maxMinutes = Math.max(...weekly.map((d) => d.minutes), 1);

  return (
    <div className="card">
      <h2>Dashboard</h2>

      <div className="metrics">
        <div className="metric-box">Tasks<br />{stats.taskCount}</div>
        <div className="metric-box">Done<br />{stats.doneCount}</div>
        <div className="metric-box">Completion<br />{stats.completionRate}%</div>
        <div className="metric-box">Focus Minutes<br />{stats.totalFocusMinutes}</div>
      </div>

      <h3>Last 7 Days Focus</h3>

      <div>
        {weekly.map((item) => {
          const width = item.minutes === 0 ? 56 : Math.max((item.minutes / maxMinutes) * 500, 56);

          return (
            <div key={item.date} className="bar-row">
              <span>{item.date}</span>
              <div
                className="bar"
                style={{ width: `${width}px` }}
              >
                {item.minutes} min
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default Dashboard;