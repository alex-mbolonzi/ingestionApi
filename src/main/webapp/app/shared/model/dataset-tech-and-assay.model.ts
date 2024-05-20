export interface IDatasetTechAndAssay {
  id?: number;
  datasetTechAndAssayId?: number;
  techniqueAndAssay?: string;
  datasetIdId?: number;
}

export class DatasetTechAndAssay implements IDatasetTechAndAssay {
  constructor(
    public id?: number,
    public datasetTechAndAssayId?: number,
    public techniqueAndAssay?: string,
    public datasetIdId?: number,
  ) {}
}
