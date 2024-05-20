import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetStudyDetailComponent } from 'app/entities/dataset-study/dataset-study-detail.component';
import { DatasetStudy } from 'app/shared/model/dataset-study.model';

describe('Component Tests', () => {
  describe('DatasetStudy Management Detail Component', () => {
    let comp: DatasetStudyDetailComponent;
    let fixture: ComponentFixture<DatasetStudyDetailComponent>;
    const route = { data: of({ datasetStudy: new DatasetStudy(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetStudyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DatasetStudyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetStudyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.datasetStudy).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
