import { useState } from "react";

function TaskPanel({ tasks, refresh }) {
  const [title, setTitle] = useState("");
  const [estimatedMinutes, setEstimatedMinutes] = useState(25);

  const createTask = async () => {
    if (!title.trim()) return;

    await fetch("http://localhost:8080/api/tasks", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        title,
        estimatedMinutes,
        status: "TODO",
      }),
    });

    setTitle("");
    setEstimatedMinutes(25);
    refresh();
  };

  const markDone = async (task) => {
    await fetch(`http://localhost:8080/api/tasks/${task.id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        ...task,
        status: "DONE",
      }),
    });
    refresh();
  };

  const deleteTask = async (id) => {
    await fetch(`http://localhost:8080/api/tasks/${id}`, {
      method: "DELETE",
    });
    refresh();
  };

  return (
    <div className="card">
      <h2>Tasks</h2>

      <div className="task-form">
        <input
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="New task"
        />
        <input
          type="number"
          value={estimatedMinutes}
          onChange={(e) => setEstimatedMinutes(Number(e.target.value))}
        />
        <button onClick={createTask}>Add Task</button>
      </div>

      {tasks.map((task) => (
        <div key={task.id} className="task-row">
          <span>{task.title}</span>
          <span>{task.status}</span>
          <span>{task.estimatedMinutes} min</span>
          <button onClick={() => markDone(task)}>Done</button>
          <button onClick={() => deleteTask(task.id)}>Delete</button>
        </div>
      ))}
    </div>
  );
}

export default TaskPanel;